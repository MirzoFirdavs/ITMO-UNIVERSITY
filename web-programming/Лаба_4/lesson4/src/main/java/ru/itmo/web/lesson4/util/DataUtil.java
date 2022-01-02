package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", User.Color.GREEN),
            new User(6, "pashka", "Pavel Mavrin", User.Color.RED),
            new User(9, "geranazarov555", "Georgiy Nazarov", User.Color.BLUE),
            new User(11, "tourist", "Gennady Korotkevich", User.Color.RED)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "POST1", "Hello Codeforces!\n" +
                    "\n" +
                    "On Sunday, October 10, 2021 at 12:05 Educational Codeforces Round 115 (Rated for Div. 2) will start. Note that the start time is unusual.\n" +
                    "\n" +
                    "Series of Educational Rounds continue being held as Harbour.Space University initiative! You can read the details about the cooperation between Harbour.Space University and Codeforces in the blog post.\n" +
                    "\n" +
                    "This round will be rated for the participants with rating lower than 2100. It will be held on extended ICPC rules. The penalty for each incorrect submission until the submission with a full solution is 10 minutes. After the end of the contest you will have 12 hours to hack any solution you want. You will have access to copy any solution and test it locally.\n" +
                    "\n" +
                    "You will be given 6 or 7 problems and 2 hours to solve them.\n" +
                    "\n" +
                    "The problems were invented and prepared by Alex fcspartakm Frolov, Mikhail awoo Piklyaev, Max Neon Mescheryakov and me. Also huge thanks to Mike MikeMirzayanov Mirzayanov for great systems Polygon and Codeforces.\n" +
                    "\n" +
                    "Good luck to all the participants!", 1),
            new Post(2, "POST2", "Hello, Codeforces!\n" +
                    "\n" +
                    "Welcome to the ICPC Communication Routing Challenge powered by Huawei!\n" +
                    "\n" +
                    "This challenge edition is very special, as it will be happening in conjunction with the ICPC World Finals! For those who are not participating in the finals — ICPC and Codeforces are offering a unique chance to compete during a 5-day Challenge Marathon and win amazing prizes from Huawei!\n" +
                    "\n" +
                    "ICPC Challenge Marathon (open to public, unrated): October 9-14, 2021, 00:00 UTC:", 6),
            new Post(3, "POST3", "Hello, Codeforces!\n" +
                    "\n" +
                    "First and foremost, we would like to say a massive thank you to everyone who entered and submitted their answers to the seven Kotlin Heroes competitions which were held previously: Episode 1, Episode 2, Episode 3, Episode 4, Episode 5: ICPC Round, Episode 6, and Episode 7.\n" +
                    "\n" +
                    "Ready to challenge yourself to do better? The Kotlin Heroes: Episode 8 competition will be hosted on the Codeforces platform on Thursday, October 7, 2021 at 17:35. The contest will last 2 hours 30 minutes and will feature a set of problems from simple ones, designed to be solvable by anyone, to hard ones, to make it interesting for seasoned competitive programmers.", 9),
            new Post(4, "POST4", "Hello, Codeforces!\n" +
                    "\n" +
                    "ICPC World Finals Moscow will begin on October 5, 2021 at 8:30 (UTC+3). We are thrilled to invite you to join the live broadcast of the main event of the year in the world of sports programming!\n" +
                    "\n" +
                    "For the very first time in October 2021, Moscow will host the world’s most prestigious competition for young IT talents, the ICPC World Finals Championship. The last International Collegiate Programming Contest has hosted over 60000 students from 3,514 universities in 115 countries that span the globe. October 5 more than 100 teams will compete in logic, mental speed, and strategic thinking at Russia’s main Manege Central Conference Hall.", 11)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
        data.put("menu", request.getRequestURI());
    }
}
