import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static String statement = "";
    public static Map<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        Spark.staticFileLocation("/public");
        Spark.init();

        Spark.get(
                "/",
                ((request, response) -> {
                    HashMap m = new HashMap();
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    User user = users.get(userName);
                    ArrayList<Sandwich> sandwiches = new ArrayList<>();

                    if (!users.isEmpty()){
                        users.forEach((k, v) -> {
                            ArrayList<Sandwich> userSandwiches = v.getSandwiches();
                            for (Sandwich s : userSandwiches) {
                                sandwiches.add(s);
                                s.setMine(false);
                            }
                        });
                    }

                    if (user != null) {
                        for (Sandwich s : user.getSandwiches()) {
                            s.setMine(true);
                        }
                    }

                    m.put("statement", statement);
                    m.put("user", user);
                    m.put("sandwiches", sandwiches);

                    return new ModelAndView(m, "index.html");
                }),
                new MustacheTemplateEngine()
        );

        Spark.post(
                "/login",
                ((request, response) -> {
                    String userName = request.queryParams("name");
                    String password = request.queryParams("password");
                    User user = users.get(userName);
                    Session session = request.session();

                    if (user == null) {
                        statement = "Welcome New User " + userName + ".";
                        user = new User(userName, password);
                        session.attribute("userName", userName);
                        users.put(userName, user);
                    }
                    else if (! password.equals(user.getPassword())) {
                        statement = "Incorrect Login Information.";
                    }
                    else {
                        statement = "";
                        session.attribute("userName", userName);
                    }

                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/logout",
                ((request, response) -> {
                    Session session = request.session();
                    session.invalidate();
                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/new-sandwich",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    User user = users.get(userName);
                     if (user == null) {
                         statement = "How are you not logged in?";
                         response.redirect("/");
                     }

                     String sandwichName = request.queryParams("newSandwich");

                    if (sandwichName.isEmpty()) {
                        statement = "Please Enter a name.";
                        response.redirect("/");
                    }

                    user.getSandwiches().add(0,new Sandwich(sandwichName, user.sandwichIndex));
                    user.sandwichIndex++;

                    statement = "";

                    response.redirect("/");
                    return "";
                })
        );

        Spark.post(
                "/delete-sandwich",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    User user = users.get(userName);
                    if (user == null) {
                        statement = "How are you not logged in?";
                        response.redirect("/");
                    }

                    int id = Integer.parseInt(request.queryParams("id"));
                    for (Sandwich s : user.getSandwiches()) {
                        if (s.getId() == id) {
                            user.getSandwiches().remove(s);
                            response.redirect("/");
                        }
                    }
                    return "";
                })
        );

        Spark.post(
                "/edit-sandwich",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    User user = users.get(userName);
                    if (user == null) {
                        statement = "How are you not logged in?";
                        response.redirect("/");
                    }

                    int id = Integer.parseInt(request.queryParams("id"));
                    for (Sandwich s : user.getSandwiches()) {
                        if (s.getId() == id) {
                            s.setDisplay(false);
                            response.redirect("/");
                        }
                    }
                    return "";
                })
        );

        Spark.post(
                "/accept-changes",
                ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    User user = users.get(userName);
                    if (user == null) {
                        statement = "How are you not logged in?";
                        response.redirect("/");
                    }

                    String sandwichName = request.queryParams("sandwichName");

                    int id = Integer.parseInt(request.queryParams("id"));
                    for (Sandwich s : user.getSandwiches()) {
                        if (s.getId() == id) {
                            s.setSandwichName(sandwichName);
                            s.setDisplay(true);
                            response.redirect("/");
                        }
                    }
                    return "";
                })
        );
    }
}
