package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

    private static final String IEX_BATCH_PATH = "/stock/market/batch?symbol=%s&types=quote&token=";
    private final String IEX_BATCH_URL;
    private final String token;
    private final String host;

    private Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private HttpClientConnectionManager httpClientConnectionManager;

    @Autowired
    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager, MarketDataConfig marketDataConfig) {
        this.httpClientConnectionManager = httpClientConnectionManager;

        token = marketDataConfig.getToken();
        host = marketDataConfig.getHost();
        IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + token;
    }

    /**
     * Get an IexQuote (helper method which class findAllById)
     *
     * @param ticker
     * @throws IllegalArgumentException if a given ticker is invalid
     * @throws DataRetrievalFailureException if HTTP request failed
     */
    @Override
    public Optional<IexQuote> findById(String ticker) {
        Optional<IexQuote> iexQuote;
        List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));

        if (quotes.size() == 0) {
            return Optional.empty();
        } else if (quotes.size() ==1) {
            iexQuote = Optional.of(quotes.get(0));
        } else {
            throw new DataRetrievalFailureException("Unexpected number of quotes");
        }
        return iexQuote;
    }

    /**
     * Get quotes from IEX
     * @param tickers is a list of tickers
     * @return a list of IexQuote object
     * @throws IllegalArgumentException if any ticker is invalid or ticker is empty
     * @throws DataRetrievalFailureException if HTTP request failed
     */
    @Override
    public List<IexQuote> findAllById(Iterable<String> tickers){
        // build URI, pass it to executeHttpGet
        Iterator<String> it = tickers.iterator();
        StringBuilder symbolsField = new StringBuilder();
        List<IexQuote> iexQuotes = new LinkedList<>();
        while (it.hasNext()){
            symbolsField.append(it.next().toLowerCase()+",");
        }

        if (symbolsField != null) {
            symbolsField.deleteCharAt(symbolsField.length() - 1);
            String url = buildURL(symbolsField.toString());
            Optional<String> jsonStr = null;
            try {
                jsonStr = executeHttpGet(url);
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to receive HTTP Response", e);
            }
            //Object will be a nested HastMap; the innermost hashmaps are what translate to IexQuote model
            JSONObject jo = null;
            try {
                jo = new JSONObject(jsonStr.get());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject nestedJo = null;
            JSONObject nestedNestedJo = null;
            it = tickers.iterator();
            while(it.hasNext()){
                try{
                    nestedJo = (JSONObject) jo.get(it.next().toUpperCase());
                    nestedNestedJo = (JSONObject) nestedJo.get("quote");
                    iexQuotes.add(toObjectFromJson(nestedNestedJo.toString(), IexQuote.class));
                } catch (IOException e) {
                    throw new RuntimeException("Failed to convert Json to IexQuote", e);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Not all expected quotes returned. Check if tickers are valid", e);
                }
            }
            return iexQuotes;
        }
        return null;
    }

    private IexQuote toObjectFromJson(String jsonStr, Class<IexQuote> iexQuoteClass) throws IOException {
        ObjectMapper m = new ObjectMapper();
        m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return (IexQuote) m.readValue(jsonStr, iexQuoteClass);
    }

    private String buildURL(String symbolsField) {
        return host + "/stock/market/batch?symbols=" + symbolsField + "&types=quote&token=" +token;
    }

    /**
     * Execute a get and return http entity/body as a string
     *
     * Tip: use EntityUtils.toString to process HTTP entity
     *
     * @param url resource URL
     * @return http response body or Option.empty for 404 response
     * @throws DataRetrievalFailureException if HTTP failed or status code is unexpected
     */
    private Optional<String> executeHttpGet (String url) throws IOException {
        HttpClient httpClient = getHttpClient();

        HttpGet request = new HttpGet(url);
        HttpResponse response = httpClient.execute(request);

        return Optional.of(parseResponseBody(response, 200));
    }

    /**
     * Borrow a HTTP client from the httpClientConnectionManager
     * @return a httpClient
     */
    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                //prevent connectionManager shutdown when calling httpClient.close()
                .setConnectionManagerShared(true)
                .build();
    }

    public String parseResponseBody(HttpResponse response, Integer expectedStatusCode) {
        //Check response status
        int status = response.getStatusLine().getStatusCode();
        if (status != expectedStatusCode){
            try{
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                System.out.println("Response has no entity");
            } throw new RuntimeException("Unexpected HTTP status" + status);
        }

        if (response.getEntity() == null) {
            throw new RuntimeException("Empty response body");
        }

        // Convert Response Entity to str
        String jsonStr;
        try {
            jsonStr = EntityUtils.toString(response.getEntity());
        } catch (IOException e){
            throw new RuntimeException("Failed to convert entity to String", e);
        }

        return jsonStr;
    }

    @Override
    public <S extends IexQuote> S save(S s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Iterable<IexQuote> findAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public long count() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(IexQuote iexQuote) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends IexQuote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
