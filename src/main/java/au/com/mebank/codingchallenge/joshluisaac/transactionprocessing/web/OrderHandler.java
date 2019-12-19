package au.com.mebank.codingchallenge.joshluisaac.transactionprocessing.web;

import au.com.mebank.codingchallenge.joshluisaac.Routes;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderHandler {

  @GetMapping(value = Routes.Transactions.ORDERS)
  public String renderOrder(HttpServletResponse response) {
    System.out.println(CacheControl.maxAge(120, TimeUnit.SECONDS).getHeaderValue());
    response.setHeader(
        HttpHeaders.CACHE_CONTROL, CacheControl.maxAge(120, TimeUnit.SECONDS).getHeaderValue());

    return "multiform";
  }
}
