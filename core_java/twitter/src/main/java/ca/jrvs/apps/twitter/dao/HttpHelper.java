package ca.jrvs.apps.twitter.dao;

import org.apache.http.HttpResponse;

import java.net.URI;

public interface HttpHelper {

  /**
   * Execute a HTTP Post call
   * @param uri
   * @return
   */
  HttpResponse httpPost(URI uri);

  /**
   * Execute a HTTP Get call
   * @param uri
   * @return
   */
  HttpResponse httpGet(URI uri);
}
