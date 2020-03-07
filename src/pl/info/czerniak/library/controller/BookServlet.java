package pl.info.czerniak.library.controller;

import pl.info.czerniak.library.dao.BookDAO;
import pl.info.czerniak.library.model.Book;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String isbn = req.getParameter("isbn");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String option = req.getParameter("option");
        try{
            BookDAO dao = new BookDAO();
            Book book = null;
            String operation = null;
            if("search".equals(option)){
                book = dao.read(isbn);
                operation = "search";
            }else if("add".equals(option)){
                book = new Book(isbn,title,description);
                dao.create(book);
                operation = "add";
            }else if("update".equals(option)){
                book = new Book(isbn,title,description);
                dao.update(book);
                operation = "update";
            }else if("delete".equals(option)){
                book = new Book(isbn,title,description);
                dao.delete(book);
                operation = "delete";
            }
            req.setAttribute("option",operation);
            req.setAttribute("book",book);
            req.getRequestDispatcher("result.jsp").forward(req,resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.getRequestDispatcher("error.jsp").forward(req,resp);
        }
    }
}
