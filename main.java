import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    manager manager1 = manager.getInstance();

    // retreiving athlete data
    File athletes = new File("athletes.csv");
    try (Scanner reader = new Scanner(athletes)) {
      reader.useDelimiter(",|\\n");
      reader.nextLine();
      while(reader.hasNext()) {
        String name = reader.next();
        String mass = reader.next();
        athlete athlete1 = new athlete(name, mass);
        manager1.addAthlete(athlete1);
      }
    } catch (FileNotFoundException e){
      System.out.println("Error");
      e.printStackTrace();
    }

    // retreiving workout data
    File workouts = new File("data.csv");
    try (Scanner reader = new Scanner(workouts)) {
      reader.useDelimiter(",|\\n");
      reader.nextLine();
      while(reader.hasNext()) {
        String group = reader.next();
        String exercise = reader.next();
        String reps = reader.next();
        String weight = reader.next();
        String date = reader.next();
        String name = reader.next();
        workout workout1 = new workout(group, exercise, reps, weight, date, name);
        manager1.assignWorkout(name, workout1);
      }
    } catch (FileNotFoundException e){
      System.out.println("Error");
      e.printStackTrace();
    }

    // signing in user
    String userName = "";
    do { 
      System.out.println("Enter user name: ");
      userName = scanner.nextLine();
      userName = userName.toLowerCase();
      if (manager1.findAthlete(userName) == null){
        System.out.println("This user doesn't exist");
        System.out.println("Would you like to create their profile? (y/n)");
        String profile = scanner.nextLine();
        if (profile.equals("y")){
          System.out.println("Enter New Name: ");
          String newName = scanner.nextLine();
          newName = newName.toLowerCase();
          System.out.println("Enter New Mass: ");
          String newMass = scanner.nextLine();
          newMass = newMass.toLowerCase();
          try (FileWriter writer = new FileWriter("athletes.csv", true)){
              writer.write(String.join(",", newName, newMass) + "\n");
          } catch (IOException e) {
              System.out.println("Error");
              e.printStackTrace();
          }
          athlete athlete1 = new athlete(newName, newMass);
          manager1.addAthlete(athlete1);
        }
      }
    } while (manager1.findAthlete(userName) == null);

    // command line interface
    while (true){
      System.out.println("MENU:");
      System.out.println("1: Search Last Exercise of Muscle Group");
      System.out.println("2: Search Last Weight and Reps of Exercise");
      System.out.println("3: Log an Exercise");
      System.out.println("4: Change user");
      System.out.println("5: Create a User");
      System.out.println("6: Exit Program");
      int option = scanner.nextInt();
      scanner.nextLine();

      switch(option){
        case 1 -> {
            System.out.println("Enter Muscle Group: ");
            String muscleGroup = scanner.nextLine();
            muscleGroup = muscleGroup.toLowerCase();
            manager1.findLastExercise(userName, muscleGroup);
            System.out.println("");
            }
        case 2 -> {
            System.out.println("Enter Exercise Name: ");
            String exerciseName = scanner.nextLine();
            exerciseName = exerciseName.toLowerCase();
            manager1.findExercise(userName, exerciseName);
            System.out.println("");
            }
        case 3 -> {
            System.out.println("Enter Muscle Group: ");
            String group = scanner.nextLine();
            group = group.toLowerCase();
            LocalDate today = LocalDate.now();
            System.out.println("Exercise Name: ");
            String name = scanner.nextLine();
            name = name.toLowerCase();
            System.out.println("Reps: ");
            String reps = scanner.nextLine();
            reps = reps.toLowerCase();
            System.out.println("Weight(kg): ");
            String weight = scanner.nextLine();
            weight = weight.toLowerCase();
            try (FileWriter writer = new FileWriter("data.csv", true)){
                writer.write(String.join(",", group, name, reps, weight, today.toString(), userName) + "\n");
            } catch (IOException e) {
                System.out.println("Error");
                e.printStackTrace();
            } 
            workout workout1 = new workout(group, name, reps, weight, today.toString(), userName);
            manager1.assignWorkout(userName, workout1);
            System.out.println("Exercise Logged");
            System.out.println("");
          }
        case 4 -> {
            do { 
              System.out.println("Enter user name: ");
              userName = scanner.nextLine();
              userName = userName.toLowerCase();
              if (manager1.findAthlete(userName) == null){
                System.out.println("This user doesn't exist");
                System.out.println("Would you like to create their profile? (y/n)");
                String profile = scanner.nextLine();
                if (profile.equals("y")){
                  System.out.println("Enter New Name: ");
                  String newName = scanner.nextLine();
                  newName = newName.toLowerCase();
                  System.out.println("Enter New Mass: ");
                  String newMass = scanner.nextLine();
                  newMass = newMass.toLowerCase();
                  try (FileWriter writer = new FileWriter("athletes.csv", true)){
                      writer.write(String.join(",", newName, newMass) + "\n");
                  } catch (IOException e) {
                      System.out.println("Error");
                      e.printStackTrace();
                  }
                  athlete athlete1 = new athlete(newName, newMass);
                  manager1.addAthlete(athlete1);
                }
              }
            } while (manager1.findAthlete(userName) == null);
            System.out.println("User Changed");
            System.out.println("");
            }
        case 5 -> {
            System.out.println("Enter New Name: ");
            String newName = scanner.nextLine();
            newName = newName.toLowerCase();
            System.out.println("Enter New Mass: ");
            String newMass = scanner.nextLine();
            newMass = newMass.toLowerCase();
            try (FileWriter writer = new FileWriter("athletes.csv", true)){
                writer.write(String.join(",", newName, newMass) + "\n");
            } catch (IOException e) {
                System.out.println("Error");
                e.printStackTrace();
            }
            athlete athlete1 = new athlete(newName, newMass);
            manager1.addAthlete(athlete1);
            userName = newName;
            System.out.println("");
        }
        case 6 -> {
            System.out.println("Exiting Program");
            return;
            }
        default -> System.out.println("Invalid Option, try again");
      }
    }
  }
}

// class to store workout data
class workout{
  private String group;
  private String exercise;
  private String reps;
  private String weight;
  private String date;
  private String name;

  public workout(String group, String exercise, String reps, String weight, String date, String name){
    this.group = group;
    this.exercise = exercise;
    this.reps = reps;
    this.weight = weight;
    this.date = date;
    this.name = name;
  }

  public String getExercise(){
    return exercise;
  }

  public void setExercise(String exercise){
    this.exercise = exercise;
  }

  public String getGroup(){
    return group;
  }

  public void setGroup(String group){
    this.group = group;
  }

  public String getReps(){
    return reps;
  }

  public void setReps(String reps){
    this.reps = reps;
  }

  public String getWeight(){
    return weight;
  }

  public void setWeight(String weight){
    this.weight = weight;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getDate(){
    return date;
  }
  
  public void setDate(String date){
    this.date = date;
  }
}

// class to store athlete data
class athlete{
  private String name;
  private String mass;
  private List<workout> workouts = new ArrayList<>();

  public athlete(String name, String mass){
    this.name = name;
    this.mass = mass;
    this.workouts = new ArrayList<>();
  }

  public List<workout> getWorkouts(){
    return workouts;
  }

  public void addWorkout(workout workout){
    workouts.add(workout);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}

// singleton software pattern
class manager{
  private static manager instance;
  private List<athlete> athletes = new ArrayList<>();

  public static manager getInstance() {
    if (instance == null) {
      instance = new manager();
    }
    return instance;
  }

  public void addAthlete(athlete athlete){
    athletes.add(athlete);
  }

  public athlete findAthlete(String name) {
    for (athlete athlete:athletes){
      if(athlete.getName().equalsIgnoreCase(name)){
        return athlete;
      }
    }
    return null;
  }

  public void assignWorkout(String name, workout workout){
    athlete athlete = findAthlete(name);
    if (athlete != null) {
      athlete.addWorkout(workout);
    } else {
      System.out.println("Athlete not found");
    }
  }

  public void findExercise(String name, String exercise){
    athlete athlete = findAthlete(name);
    String reps = "";
    String weight = "";
    LocalDate date = LocalDate.of(0001,1,1);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd");
    for (workout w: athlete.getWorkouts()){
      if (w.getExercise().equals(exercise)){
        if (date.isBefore(LocalDate.parse(w.getDate(), formatter))){
          date = LocalDate.parse(w.getDate(), formatter);
          reps = w.getReps();
          weight = w.getWeight();
        }
      }
    }
    if (reps.equals("")){
      System.out.println("Exercise not Found");
    } else {
      System.out.println("Exercise: " + exercise + ", Weight: " + weight + ", Reps: " + reps + ", Date: " + date.toString());
    }
  }

  public void findLastExercise(String name, String group){
    athlete athlete = findAthlete(name);
    String exercise = "";
    LocalDate date = LocalDate.of(0001,1,1);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    for (workout w: athlete.getWorkouts()){
      if (w.getGroup().equals(group)){
        if (date.isBefore(LocalDate.parse(w.getDate(), formatter))){
          date = LocalDate.parse(w.getDate(), formatter);
          exercise = w.getExercise();
        }
      }
    }
    if (exercise.equals("")){
      System.out.println("Exercise not Found");
    } else {
      System.out.println("The last " + group + " exercise was: " + exercise + ", on date: " + date.toString());
    }
  }
}