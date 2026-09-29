package murach.email;

import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/emailList")
public class EmailListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String firstName = clean(request.getParameter("firstName"));
        String lastName = clean(request.getParameter("lastName"));
        String email = clean(request.getParameter("email"));

        if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
            request.setAttribute("errorMessage", "Please fill in all fields.");
            request.getRequestDispatcher("/thanks.jsp").forward(request, response);
            return;
        }

        String from = System.getenv("MAIL_FROM");
        if (from == null || from.isBlank()) {
            from = System.getenv("MAIL_USERNAME");
        }

        String subject = "Welcome to our email list";
        String body = "Dear " + firstName + ",\n\n"
                + "Thanks for joining our email list. "
                + "We'll make sure to send you announcements about new products "
                + "and promotions.\n\n"
                + "Have a great day and thanks again!\n\n"
                + "Chapter 14 JavaMail Demo";

        try {
            MailUtilGmail.sendMail(email, from, subject, body, false);
            request.setAttribute("firstName", firstName);
            request.setAttribute("email", email);
            request.setAttribute("successMessage", "Your welcome email was sent successfully.");
        } catch (MessagingException | IllegalStateException e) {
            getServletContext().log(
                    "EMAIL ERROR: " + e.getClass().getName()
                    + " - " + e.getMessage(), e);

            request.setAttribute("errorMessage",
                    "Email error: " + e.getClass().getSimpleName()
                    + " - " + e.getMessage());

        } catch (Exception e) {
            getServletContext().log(
                    "UNEXPECTED EMAIL ERROR: "
                    + e.getClass().getName()
                    + " - " + e.getMessage(), e);

            request.setAttribute("errorMessage",
                    "Unexpected email error: "
                    + e.getClass().getSimpleName()
                    + " - " + e.getMessage());
        }

        request.getRequestDispatcher("/thanks.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
