package servlet.user;

import dao.ProductCategoryDao;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Product;
import model.ProductCategory;

@WebServlet(name = "Application", urlPatterns = {"/app"})
public class Application extends HttpServlet {

    RequestDispatcher rd = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

//        if (session.getAttribute("name") == null) {
//            response.sendRedirect(request.getContextPath() + "/login");
//        }

        controller.Administrator list = new controller.Administrator();

        List<Product> listProducts = list.findProducts();
        session.setAttribute("LIST_ALL_PRODUCTS", listProducts);

        // Get all the categories
        ProductCategoryDao productCategoryDao = new ProductCategoryDao();
        List<ProductCategory> productCategoryList = productCategoryDao.
                getProductCategories();
        session.setAttribute("productCategoryList", productCategoryList);

        rd = request.getRequestDispatcher("/app/index.jsp");
        rd.include(request, response);

        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
