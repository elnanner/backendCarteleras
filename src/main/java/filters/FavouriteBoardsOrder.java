package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Servlet Filter implementation class FavouriteBoardsOrder
 */
@WebFilter("/boards/*")
public class FavouriteBoardsOrder implements Filter {

    /**
     * Default constructor. 
     */
    public FavouriteBoardsOrder() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("pre filtro!!!!boards");
		//verificar permisos
		/*
		Gson gson = new GsonBuilder().serializeNulls().create();//new Gson();
		String json = httpEntity.getBody();
		JsonObject dataJson = gson.fromJson(json, JsonObject.class);*/
		chain.doFilter(request, response);
		System.out.println("postfiltro!!!!boards");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
