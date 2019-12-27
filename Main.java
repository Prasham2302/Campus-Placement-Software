package com.company;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Student{
    //Student class containing all the information of a Student
    float cgpa;//store the CGPA
    String branch;//Stores the branch name
    int roll_no;//Stores the roll number
    boolean status;//Stores the status, if true then placed else not placed
    String company;//Stores the name of the company in which it got placed
    int score;//Stores the Score of the company in which it got placed
    Student(int r,String b,float c) {
        /*Constructor for the class*/
        status = false;
        roll_no = r;
        cgpa = c;
        branch = b;
    }
    public void setScore(int i){
        //Setter for score
        score=i;
    }
    public boolean getStatus(){
        //getter for status
        return status;
    }
    public void setCompany(String c){
        //Setter for company
        status = true;
        company = c;
    }
    public void printDetails(){
        //Function to print Details of the Student
        System.out.println(roll_no);
        System.out.println(cgpa);
        System.out.println(branch);
        if(status == false){
            System.out.println("Placement Status: Not Placed");
        }
        else{
            System.out.println("Placement Status: "+ company);
        }
    }
}
class company{
    //company class containing all the information of the company
    String name;// its name
    int total_courses;// Number of courses
    String[] courses;// Name of all the courses company is looking for
    int requirement;
    boolean status;
    LinkedList<Student> selectedstudents = new LinkedList<>();//list of all selected students

    company(String n,int tc,String[] c,int r){
        //constructor for the class
        name = n;
        total_courses = tc;
        requirement = r;
        courses = new String[tc];
        for(int i  =0;i<c.length;i++) {
            courses[i] = c[i];
        }
        status = true;
    }
    public void printDetails(){
        //All the details to be printed the=rough this function
        System.out.println(name);
        System.out.println("Course Criteria");
        for(int i = 0;i<total_courses;i++){
            System.out.println(courses[i]);
        }
        System.out.println("Number of Required Students: "+requirement);
        if(status == false){
            System.out.println("Application Status: Closed");
        }
        else{
            System.out.println("Application Status: Open");
        }
    }
}


public class Main {

    public static void main(String[] args) throws IOException {
        // write your code here

        System.out.print("Number of Students to be placed: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String line = br.readLine();
        int n = Integer.parseInt(line);
        LinkedList<Student> studentlist = new LinkedList<>();
        LinkedList<company> companies = new LinkedList<>();
        for(int i = 0;i<n;i++){
            //Getting list of Students for placement
            String[] line1 = br.readLine().trim().split("\\s+");
            float cgpa = Float.parseFloat(line1[0]);
            String branch = line1[1];
            Student temp = new Student(i+1,branch,cgpa);
            studentlist.add(temp);

        }
        System.out.println("----Students Registered----");
        while(studentlist.size()!=0){

            // Quering while the all the Students are not placed
            String[] line2 = br.readLine().trim().split("\\s+");

            //getting the Query
            int query = Integer.parseInt(line2[0]);

            /////////////////////////////
            if(query == 1){
                //Adding company to the placement

                String name = br.readLine();
                System.out.print("Number of eligible courses: ");
                int tc = Integer.parseInt(br.readLine());
                String[] courselist = new String[tc];
                for(int i = 0;i<tc;i++){
                    courselist[i] = br.readLine();
                }
                System.out.print("Number of Required Student: ");
                int req = Integer.parseInt(br.readLine());
                company temp = new company(name,tc,courselist,req);
                companies.add(temp);
                temp.printDetails();

                //Getting scores of technical round for the eligible students

                System.out.println("Enter Scores of Technical Round:");
                for(int i = 0;i<studentlist.size();i++){
                    //Searching for the Eligible Students
                    String b = studentlist.get(i).branch;
                    for(int j = 0;j<tc;j++){
                        if(b.equals(courselist[j])){
                            System.out.print("Enter the score for Roll Number "+studentlist.get(i).roll_no+" :");
                            int currentscore = Integer.parseInt(br.readLine());
                            studentlist.get(i).setScore(currentscore);
                        }
                    }
                }
            }

            ///////////////////////////////

            if(query == 2){
                //Removing Accounts for the Students who are placed
                System.out.println("Accounts removed for Students with Roll number: ");
                for(int i = 0;i<studentlist.size();i++){
                    if(studentlist.get(i).status == true){
                        System.out.println(studentlist.get(i).roll_no);
                        studentlist.remove(i);
                    }
                }
            }

            ////////////////////////////////

            if(query == 3) {
                //Removing Accounts for the Company who have fullfilled their Requirements
                System.out.println("Account removed for Company: ");
                for(int i = 0;i<companies.size();i++){
                    if(companies.get(i).status == false){
                        System.out.println(companies.get(i).name);
                        companies.remove(i);
                    }
                }
            }

            ///////////////////////////////

            if(query == 4){
                //Traversing and counting the unplaced Students
                int count = 0;
                for(int i = 0;i<studentlist.size();i++){
                    if(studentlist.get(i).status == false) count++;
                }
                System.out.println(count+" Students Left");
            }

            ////////////////////////////////

            if(query == 5){
                //Printing the name of companies whose application are still open
                for(int i = 0;i<companies.size();i++){
                    if(companies.get(i).status == true)
                        System.out.println(companies.get(i).name);
                }
            }

            ////////////////////////////

            if(query == 6){
                //Placing all possible Students in the given Company
                String name = line2[1];
                company temp = null;
                int pos = 0;
                //finding the pos of company in the list of company
                for(int i = 0;i<companies.size();i++){
                    if(companies.get(i).name.equals(name)){
                        temp = companies.get(i);
                        pos = i;
                    }
                }

                //custom comparator for the priority queue
                //Compares the technical round Score and the CGPA
                Comparator<Student> StudentComparator = new Comparator<Student>() {
                    // Overriding compare()method of Comparator
                    // for descending order of Score
                    public int compare(Student s1, Student s2) {
                        if (s1.score < s2.score)
                            return 1;
                        else if (s1.score > s2.score)
                            return -1;
                        else{
                            if(s1.cgpa<s2.cgpa)
                                return 1;
                            else
                                return -1;
                        }
                    }
                };
                //Priority Queue for the Students
                // Sorting them on basis of Technical round score and CGPA
                PriorityQueue<Student> applicants = new PriorityQueue<Student>(n,StudentComparator);
                for(int i = 0;i<studentlist.size();i++){
                    String b = studentlist.get(i).branch;
                    for(int j = 0;j<temp.total_courses;j++){
                        if(temp.courses[j].equals(b)){
                            applicants.add(studentlist.get(i));
                        }
                    }
                }

                //Selecting the required amount of students
                while(temp.selectedstudents.size()!=temp.requirement){
                    Student t = applicants.poll();
                    temp.selectedstudents.add(t);
                }

                //Printing the Selected Student
                System.out.println("Roll Number of Selected Student: ");
                for(int i = 0;i<temp.selectedstudents.size();i++){
                    Student st = temp.selectedstudents.get(i);
                    for(int j = 0;j<studentlist.size();j++){
                        if(studentlist.get(j).roll_no == st.roll_no){
                            studentlist.get(j).status = true;
                            studentlist.get(j).company = temp.name;
                            System.out.println(studentlist.get(j).roll_no);
                        }
                    }
                }
                temp.status = false;
                //Modifying the list accordingly
                companies.remove(pos);
                companies.add(temp);
            }

            ////////////////////////////

            if(query == 7){

                //Displaying the Details of the required company
                String name = line2[1];
                for(int i = 0;i<companies.size();i++){
                    if(companies.get(i).name.equals(name)){
                        companies.get(i).printDetails();
                    }
                }
            }

            ///////////////////////////////

            if(query == 8){
                //Displaying the Details of the required Student
                int roll = Integer.parseInt(line2[1]);
                for(int i = 0;i<studentlist.size();i++){
                    Student temp = studentlist.get(i);
                    if(temp.roll_no == roll){
                        temp.printDetails();
                        break;
                    }
                }
            }

            //////////////////////////////

            if(query == 9) {
                //Displaying the Student appliction status i.e.
                // to all the companies he has applied to
                int roll = Integer.parseInt(line2[1]);
                int flag = 0;
                for (int i = 0; i < studentlist.size(); i++) {
                    if (roll == studentlist.get(i).roll_no) {
                        System.out.println(studentlist.get(i).company + " " + studentlist.get(i).score);
                        flag = 1;
                        break;
                    }
                }
                if (flag == 0) {
                    System.out.println("No student with the given roll number has an account.");
                }
            }

            //////////////////////////////

        }

    }
}