import java.io.*;
import java.util.*;
import java.lang.*;

class Students
{
    String branch,company_selected="";

    ArrayList<String> company_name=new ArrayList<String>();
    ArrayList<Float> tech_score=new ArrayList<Float>();

    int roll_no;
    Float cgpa,temp_tech_score;

    Students(String branch,Float cgpa,int roll_no)
    {
        this.branch=branch;
        this.cgpa=cgpa;
        this.roll_no=roll_no;
        this.temp_tech_score=0.0f;        
    }

    void add_company(String comp,Float marks)
    {
        company_name.add(comp);
        tech_score.add(marks);
    }
}

class sortbymarks implements Comparator<Students>
{
    public int compare(Students a,Students b)
    {
        if (a.temp_tech_score>b.temp_tech_score)return -1;
        else if (a.temp_tech_score<b.temp_tech_score)return 1;
        else
        {
            if (a.cgpa>=b.cgpa)return -1;
            else return 1;
        }
    }
}

class sortbyroll_no implements Comparator<Students>
{
    public int compare(Students a, Students b)
    {
        if (a.roll_no>b.roll_no)return 1;
        else return -1;
    }
}

class Company{
    String name;
    String[] course_criteria;
    int required,status=1,have=0,total_course_criteria;   //status =0 closed      =1 open

    ArrayList<Integer> selected_candidates=new ArrayList<Integer>();
    
    void set_coursesize(int n)
    {
        total_course_criteria=n;
        course_criteria=new String[n];
    }

    void selected_students(int roll)
    {
        selected_candidates.add(roll);
    }


}

class Main{
    public static void main(String[] args) throws IOException{

        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

        int size=Integer.parseInt(buffer.readLine());
        ArrayList<Students> student=new ArrayList<Students>(size);

        //input for info of students
        for (int i=0;i<size;i++)
        {
            StringTokenizer info = new StringTokenizer(buffer.readLine());
            
            Float cgpa=Float.parseFloat(info.nextToken());
            String branch=info.nextToken();            

            Students stud=new Students(branch,cgpa,i+1);
            student.add(stud);
        }

        int unplaced=size;

        LinkedList<Company> companylist = new LinkedList<Company>();

        while(unplaced>0)
        {
            StringTokenizer choice_string = new StringTokenizer(buffer.readLine());
            int choice=Integer.parseInt(choice_string.nextToken());

            if (choice==1)
            {
                Company temp_company=new Company();
                temp_company.name=buffer.readLine();

                System.out.print("Number of Eligible courses = ");
                int c_no=Integer.parseInt(buffer.readLine());
                temp_company.set_coursesize(c_no);                
                for (int i=0;i<c_no;i++)
                {
                    temp_company.course_criteria[i]=buffer.readLine();
                }

                System.out.print("Number Of Required Students = ");
                temp_company.required=Integer.parseInt(buffer.readLine());

                // Printing company detail
                System.out.println(temp_company.name+"\nCourse criteria");
                for (int i=0;i<c_no;i++)
                {
                    System.out.println(temp_company.course_criteria[i]);
                }
                System.out.println("Number of Required Students = "+temp_company.required);
                System.out.println("Application Status = OPEN");                
                

                //finding eligible students                
                for (int i=0;i<size;i++)
                {
                    if (student.get(i).company_selected.equals(""))
                    {
                        for (int loop=0;loop<c_no;loop++)
                        {
                            if (student.get(i).branch.equals(temp_company.course_criteria[loop]))
                            {
                                System.out.print("Enter score for Roll no. "+student.get(i).roll_no+"\n");
                                Float marks=Float.parseFloat(buffer.readLine());
                                student.get(i).add_company(temp_company.name,marks);
                            }
                        }
                    }
                }

                //adding company to company linked list
                companylist.add(temp_company);

            }

            else if (choice==2)
            {
                System.out.println("Account removed for");
                for(int i=0;i<size;i++)
                {                    
                    if (!student.get(i).company_selected.equals(""))System.out.println(student.get(i).roll_no);                   
                }
            }

            else if (choice==3)
            {
                ListIterator<Company> iter = companylist.listIterator();
                System.out.println("Account removed for");
                while(iter.hasNext())
                {
                    Company temp=iter.next();
                    if (temp.status==0)
                    {
                        System.out.println(temp.name);
                    }
                }
            }

            else if (choice==4)
            {
                int count=0;
                for(int i=0;i<size;i++)
                {
                    if(student.get(i).company_selected.equals(""))count++;
                }
                System.out.println(count+" students left");
            }

            else if (choice==5)
            {
                ListIterator<Company> iter = companylist.listIterator();
                while(iter.hasNext())
                {
                    Company temp=iter.next();
                    if (temp.status==1)
                    {
                        System.out.println(temp.name);
                    }
                }
               
            }

            else if (choice==6)
            {
                String companyname=choice_string.nextToken();

                //updating student technical round marks wrt company
                for (int i=0;i<size;i++)
                {
                    if ((student.get(i).company_selected.equals("")) && (student.get(i).company_name.size()!=0))
                    {                     
                        for (int u=0;u<student.get(i).company_name.size();u++)
                        {
                            if (student.get(i).company_name.get(u).equals(companyname))student.get(i).temp_tech_score=student.get(i).tech_score.get(u);                           
                        }                        
                    }
                }

                //sorting student on the basis on cgpa and technical rounf marks
                Collections.sort(student,new sortbymarks());


                ListIterator<Company> iter = companylist.listIterator();
                while(iter.hasNext())
                {
                    Company temp=iter.next();
                    //finding company name in linked list
                    if (temp.name.equals(companyname))
                    {
                        if(temp.status==0)
                        {
                            System.out.println(companyname+" account was removed");
                            break;
                        }

                        System.out.println("Roll number of Selected students");

                        int i=-1;
                        while (i<size-1 && temp.have<temp.required)
                        {
                            i++;
                            //finding unplaced students
                            if (student.get(i).company_selected.equals(""))
                            {
                                for (int loop=0;loop<temp.total_course_criteria;loop++)
                                {
                                    if (student.get(i).branch.equals(temp.course_criteria[loop]))
                                    {                                        
                                        System.out.println(student.get(i).roll_no);
                                        student.get(i).company_selected=companyname;
                                        temp.selected_students(student.get(i).roll_no);
                                        temp.have++;
                                        unplaced--;
                                    }
                                }
                            }
                        }

                        if (temp.have==temp.required)temp.status=0;
                        break;
                    }
                }

                Collections.sort(student,new sortbyroll_no());
            }

            else if (choice==7)
            {
                String to_search=choice_string.nextToken();
                ListIterator<Company> iter = companylist.listIterator();
                while(iter.hasNext())
                {
                    Company temp=iter.next();
                    if (temp.name.equals(to_search))
                    {
                        System.out.println(temp.name+"\nCourse criteria");
                        for (int i=0;i<temp.total_course_criteria;i++)
                        {
                            System.out.println(temp.course_criteria[i]);
                        }
                        System.out.println("Number of Required Students = "+temp.required);
                        if (temp.status==0)System.out.println("Application status = CLOSED");
                        else System.out.println("Application status = OPEN");
                        break;
                    }
                }
            }

            else if (choice==8)
            {
                int roll=Integer.parseInt(choice_string.nextToken());

                if (roll>size || roll<1)
                {
                    System.out.println("No student found");
                    continue;
                }

                for (int i=0;i<size;i++)
                {
                    if (student.get(i).roll_no==roll)
                    {
                        roll=i;
                        break;
                    }
                }
                
                System.out.println(student.get(roll).roll_no+"\n"+student.get(roll).cgpa+"\n"+student.get(roll).branch);
                if (student.get(roll).company_selected.equals(""))System.out.println("Placement status: Not Placed");
                else System.out.println("Placement status: Placed "+"\n"+student.get(roll).company_selected);
            }

            else
            {
                int roll=Integer.parseInt(choice_string.nextToken());

                if (roll>size || roll<1)
                {
                    System.out.println("No student found");
                    continue;
                }

                for (int i=0;i<size;i++)
                {
                    if (student.get(i).roll_no==roll)
                    {
                        roll=i;
                        break;
                    }
                }


                if (!student.get(roll).company_selected.equals(""))System.out.println("No student with the given roll number has an account.");  
                else if (student.get(roll).company_name.size()==0)System.out.println("No technical round attempted");
                else
                {
                    for (int i=0;i<student.get(roll).company_name.size();i++)
                    {
                        System.out.println(student.get(roll).company_name.get(i)+" "+student.get(roll).tech_score.get(i));
                    }
                }             



            }
        }
        System.out.println("You exit from the program");

    }
}