import java.util.*;

class StressRecord {
    String dateTime;
    double sleep;
    double study;
    double screen;
    double breakTime;
    double exercise;
    int pressure;
    int score;
    String level;

    StressRecord(String dateTime, double sleep, double study, double screen,
                 double breakTime, double exercise, int pressure, int score, String level) {
        this.dateTime = dateTime;
        this.sleep = sleep;
        this.study = study;
        this.screen = screen;
        this.breakTime = breakTime;
        this.exercise = exercise;
        this.pressure = pressure;
        this.score = score;
        this.level = level;
    }

    void display() {
        System.out.println("\nDate & Time : " + dateTime);
        System.out.println("Sleep       : " + sleep + "h");
        System.out.println("Study       : " + study + "h");
        System.out.println("Screen      : " + screen + "h");
        System.out.println("Break       : " + breakTime + "h");
        System.out.println("Exercise    : " + exercise + "h");
        System.out.println("Pressure    : " + pressure);
        System.out.println("Score       : " + score + "/100");
        System.out.println("Level       : " + level);
    }
}

class Reminder {
    String icon;
    String label;
    String time;

    Reminder(String icon, String label, String time) {
        this.icon = icon;
        this.label = label;
        this.time = time;
    }

    void display() {
        System.out.println(icon + " " + label + " at " + time);
    }
}

class RoutineNode {
    String activity;
    RoutineNode next;

    RoutineNode(String activity) {
        this.activity = activity;
    }
}

class RoutineLinkedList {
    RoutineNode head;

    void add(String activity) {
        RoutineNode node = new RoutineNode(activity);
        if (head == null) {
            head = node;
            return;
        }
        RoutineNode temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }
        temp.next = node;
    }

    void display() {
        if (head == null) {
            System.out.println("No routine created yet.");
            return;
        }
        System.out.println("\nCustom Routine:");
        RoutineNode temp = head;
        int i = 1;
        while (temp != null) {
            System.out.println(i + ". " + temp.activity);
            temp = temp.next;
            i++;
        }
    }

    List<String> toList() {
        List<String> list = new ArrayList<>();
        RoutineNode temp = head;
        while (temp != null) {
            list.add(temp.activity);
            temp = temp.next;
        }
        return list;
    }
}

class User {
    String name;
    String email;
    String password;
    ArrayList<StressRecord> history;
    ArrayList<Reminder> reminders;
    RoutineLinkedList routine;
    int[] weeklyData;

    User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.history = new ArrayList<>();
        this.reminders = new ArrayList<>();
        this.routine = new RoutineLinkedList();
        this.weeklyData = new int[]{0, 0, 0, 0, 0, 0, 0};
    }
}

class ZenStudentSystem {
    HashMap<String, User> users;
    User currentUser;
    Stack<String> actionStack;
    Queue<Reminder> reminderQueue;
    Scanner sc;

    ZenStudentSystem() {
        users = new HashMap<>();
        actionStack = new Stack<>();
        reminderQueue = new LinkedList<>();
        sc = new Scanner(System.in);
        seedDemoUsers();
    }

    void seedDemoUsers() {
        users.put("demo@college.edu", new User("Demo User", "demo@college.edu", "demo@123"));
        users.put("student@college.edu", new User("Student", "student@college.edu", "student@123"));
    }

    void signup() {
        System.out.print("Full name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        if (users.containsKey(email)) {
            System.out.println("Account already exists.");
            return;
        }

        System.out.print("Password: ");
        String password = sc.nextLine();

        users.put(email, new User(name, email, password));
        System.out.println("Account created successfully.");
    }

    boolean login() {
        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = users.get(email);
        if (user != null && user.password.equals(password)) {
            currentUser = user;
            System.out.println("Login successful. Welcome, " + currentUser.name + "!");
            return true;
        }

        System.out.println("Invalid email or password.");
        return false;
    }

    int calcStress(double sleep, double study, double screen, double breakTime, double exercise, int pressure) {
        int score = 50;

        if (sleep < 5) score += 25;
        else if (sleep < 6) score += 15;
        else if (sleep < 7) score += 5;
        else if (sleep <= 9) score -= 10;
        else score += 5;

        if (study > 12) score += 25;
        else if (study > 10) score += 15;
        else if (study > 8) score += 8;
        else if (study >= 4) score -= 5;
        else if (study < 2) score += 10;

        if (screen > 8) score += 20;
        else if (screen > 6) score += 12;
        else if (screen > 4) score += 6;
        else if (screen <= 2) score -= 5;

        if (breakTime < 0.5) score += 15;
        else if (breakTime < 1) score += 8;
        else if (breakTime <= 2) score -= 8;
        else if (breakTime > 3) score += 5;

        if (exercise == 0) score += 10;
        else if (exercise >= 0.5) score -= 10;

        score += pressure;

        if (score < 0) score = 0;
        if (score > 100) score = 100;

        return score;
    }

    String getLevel(int score) {
        if (score < 35) return "Low Stress";
        if (score < 65) return "Moderate Stress";
        return "High Stress";
    }

    void analyzeStress() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        System.out.print("Sleep hours: ");
        double sleep = Double.parseDouble(sc.nextLine());

        System.out.print("Study hours: ");
        double study = Double.parseDouble(sc.nextLine());

        System.out.print("Screen time: ");
        double screen = Double.parseDouble(sc.nextLine());

        System.out.print("Break time: ");
        double breakTime = Double.parseDouble(sc.nextLine());

        System.out.print("Exercise time: ");
        double exercise = Double.parseDouble(sc.nextLine());

        System.out.print("Pressure (0 / 10 / 20 / 35): ");
        int pressure = Integer.parseInt(sc.nextLine());

        int score = calcStress(sleep, study, screen, breakTime, exercise, pressure);
        String level = getLevel(score);

        StressRecord record = new StressRecord(
                new Date().toString(),
                sleep, study, screen, breakTime, exercise, pressure, score, level
        );

        currentUser.history.add(0, record);

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK) - 1;
        currentUser.weeklyData[day] = score;

        actionStack.push("Analyzed stress score: " + score);

        System.out.println("\n===== RESULT =====");
        record.display();

        showSuggestions(record);
    }

    void showSuggestions(StressRecord r) {
        System.out.println("\nSuggestions:");
        if (r.sleep < 7) System.out.println("- Get 7 to 9 hours of sleep.");
        if (r.study > 8) System.out.println("- Reduce study overload and use Pomodoro technique.");
        if (r.screen > 4) System.out.println("- Reduce non-study screen time.");
        if (r.breakTime < 1) System.out.println("- Take more breaks.");
        if (r.exercise == 0) System.out.println("- Add 20 to 30 minutes of exercise.");
        if (r.score >= 65) System.out.println("- High stress detected. Consider rest and talking to someone.");
        if (r.score < 35) System.out.println("- Good balance. Maintain your routine.");
    }

    void showHistory() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        if (currentUser.history.isEmpty()) {
            System.out.println("No analysis history found.");
            return;
        }

        System.out.println("\n===== ANALYSIS HISTORY =====");
        int i = 1;
        for (StressRecord r : currentUser.history) {
            System.out.println(i + ". " + r.dateTime + " | Score: " + r.score + " | " + r.level);
            i++;
        }
    }

    void sortHistoryByScore() {
        if (currentUser == null || currentUser.history.isEmpty()) {
            System.out.println("No history available.");
            return;
        }

        ArrayList<StressRecord> sorted = new ArrayList<>(currentUser.history);
        sorted.sort((a, b) -> Integer.compare(b.score, a.score));

        System.out.println("\n===== HISTORY SORTED BY SCORE =====");
        for (StressRecord r : sorted) {
            System.out.println(r.dateTime + " | Score: " + r.score + " | " + r.level);
        }
    }

    void addReminder() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        System.out.print("Reminder icon: ");
        String icon = sc.nextLine();

        System.out.print("Reminder label: ");
        String label = sc.nextLine();

        System.out.print("Reminder time (HH:MM): ");
        String time = sc.nextLine();

        Reminder reminder = new Reminder(icon, label, time);
        currentUser.reminders.add(reminder);
        reminderQueue.offer(reminder);
        actionStack.push("Added reminder: " + label);

        System.out.println("Reminder added successfully.");
    }

    void showReminders() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        if (currentUser.reminders.isEmpty()) {
            System.out.println("No reminders yet.");
            return;
        }

        System.out.println("\n===== REMINDERS =====");
        for (Reminder r : currentUser.reminders) {
            r.display();
        }
    }

    void processNextReminder() {
        Reminder r = reminderQueue.poll();
        if (r == null) {
            System.out.println("No reminders in queue.");
        } else {
            System.out.println("Processing reminder: " + r.label + " at " + r.time);
        }
    }

    void addRoutineActivity() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        System.out.print("Enter activity: ");
        String activity = sc.nextLine();

        currentUser.routine.add(activity);
        actionStack.push("Added routine activity: " + activity);

        System.out.println("Routine activity added.");
    }

    void showRoutine() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        currentUser.routine.display();
    }

    void compareWithRecommendedRoutine() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        List<String> recommended = Arrays.asList(
                "Wake Up",
                "Exercise",
                "Breakfast",
                "Study Session",
                "Short Break",
                "Lunch",
                "Relaxation",
                "Evening Study",
                "Dinner",
                "Sleep"
        );

        List<String> custom = currentUser.routine.toList();

        System.out.println("\n===== ROUTINE COMPARISON =====");
        System.out.println("Recommended Activities: " + recommended.size());
        System.out.println("Your Activities        : " + custom.size());

        for (String rec : recommended) {
            if (custom.contains(rec)) {
                System.out.println("✓ " + rec);
            } else {
                System.out.println("✗ Missing: " + rec);
            }
        }
    }

    void showWeeklyTrend() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        System.out.println("\n===== WEEKLY STRESS TREND =====");
        for (int i = 0; i < 7; i++) {
            System.out.println(days[i] + " : " + currentUser.weeklyData[i]);
        }
    }

    void undoLastAction() {
        if (actionStack.isEmpty()) {
            System.out.println("No recent actions.");
        } else {
            System.out.println("Undo action: " + actionStack.pop());
        }
    }

    void showMenu() {
        while (true) {
            System.out.println("\n===== ZENSTUDENT JAVA DSA MENU =====");
            System.out.println("1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Analyze Stress");
            System.out.println("4. Show History");
            System.out.println("5. Sort History by Score");
            System.out.println("6. Add Reminder");
            System.out.println("7. Show Reminders");
            System.out.println("8. Process Next Reminder (Queue)");
            System.out.println("9. Add Routine Activity");
            System.out.println("10. Show Routine (LinkedList)");
            System.out.println("11. Compare Routine");
            System.out.println("12. Show Weekly Trend");
            System.out.println("13. Undo Last Action (Stack)");
            System.out.println("14. Exit");
            System.out.print("Enter choice: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    signup();
                    break;
                case "2":
                    login();
                    break;
                case "3":
                    analyzeStress();
                    break;
                case "4":
                    showHistory();
                    break;
                case "5":
                    sortHistoryByScore();
                    break;
                case "6":
                    addReminder();
                    break;
                case "7":
                    showReminders();
                    break;
                case "8":
                    processNextReminder();
                    break;
                case "9":
                    addRoutineActivity();
                    break;
                case "10":
                    showRoutine();
                    break;
                case "11":
                    compareWithRecommendedRoutine();
                    break;
                case "12":
                    showWeeklyTrend();
                    break;
                case "13":
                    undoLastAction();
                    break;
                case "14":
                    System.out.println("Exiting ZenStudent...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ZenStudentSystem system = new ZenStudentSystem();
        system.showMenu();
    }
}