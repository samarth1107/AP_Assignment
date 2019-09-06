import java.util.*;
import java.io.*;

class Ecommerce
{
    ArrayList<Customer> Customer_list=new ArrayList<Customer>();
    ArrayList<Merchant> Merchant_list=new ArrayList<Merchant>();
    ArrayList<Item> item=new ArrayList<Item>();
    ArrayList<String> item_category=new ArrayList<String>();
    int size=5;

    Ecommerce()
    {   
        //default class for Merchant and customer             
        Merchant_list.add(new Merchant("jack"));
        Merchant_list.add(new Merchant("john"));
        Merchant_list.add(new Merchant("james"));
        Merchant_list.add(new Merchant("jeff"));
        Merchant_list.add(new Merchant("joseph"));
        Merchant_list.get(0).setaddress("Block A, Avenue street");
        Merchant_list.get(1).setaddress("Block A, Avenue street");
        Merchant_list.get(2).setaddress("Block A, Avenue street");
        Merchant_list.get(3).setaddress("Block A, Avenue street");
        Merchant_list.get(4).setaddress("Block A, Avenue street");
        
        Customer_list.add(new Customer("ali"));
        Customer_list.add(new Customer("nobby"));
        Customer_list.add(new Customer("bruno"));
        Customer_list.add(new Customer("borat"));
        Customer_list.add(new Customer("aladeen"));
        Customer_list.get(0).setaddress("PO no 101, Delhi");
        Customer_list.get(1).setaddress("PO no 101, Delhi");
        Customer_list.get(2).setaddress("PO no 101, Delhi");
        Customer_list.get(3).setaddress("PO no 101, Delhi");
        Customer_list.get(4).setaddress("PO no 101, Delhi");
    }

    //function related to item adding or editing    
    public void additem(String name,double price,int quantity,String category,int marchant_ID) 
    {  
        //to add category in the list
        this.addcategory(category);

        int index=-1;

        //to search in the existing list
        for(int i=0;i<item.size();i++)
        {
            if (item.get(i).Name.equals(name))
            {
                index=i;
                break;                
            }
        }        

        //if not found then add the item
        if (index==-1)
        {
            if (Merchant_list.get(marchant_ID).used_slot<Merchant_list.get(marchant_ID).total_itemslot)
            { 
                item.add(new Item(name, price, quantity, category, marchant_ID));
                Merchant_list.get(marchant_ID).used_slot++;
                display(item.size()-1);
            }
            
            else{System.out.println("Out of slots");}
        }

        //otherwise error
        else
        {
            System.out.println("Item with name added before");
        }     
        
    }
    public void edititem(int code,double price,int quantity)
    {        
        item.get(code).edititem(price,quantity);
        display(code);
    }
    public void addoffer(int code,int offer)
    {
        String[] offerList=new String[10];
        offerList[0]="buy one get one";
        offerList[1]="25% off";
        item.get(code).setoffer(offerList[offer]);
    }
    
    //display functions
    public void showallproducts()
    {
        for(int i=0;i<item.size();i++)display(i);
    }   
    public void display(int index)
    {
        System.out.println((index+1)+" "+item.get(index));
    }
    public void addcategory(String category)
    {
        if(item_category.indexOf(category)==-1){item_category.add(category);}        
    }
    public void display_category()
    {
        for (int i=0;i<item_category.size();i++)
        {
            System.out.println((i+1)+") "+item_category.get(i));
        }
    }
    public void showbycategory(int code,int _merchantID)
    {
        if (_merchantID==-1)
        {
            //to search in the existing list wrt category
            for(int i=0;i<item.size();i++)
            {
                if (item.get(i).item_category.equals(item_category.get(code)))System.out.println((i+1)+" "+item.get(i));  
            }
        }
        else
        {
            //to search in the existing list wrt category
            for(int i=0;i<item.size();i++)
            {
                if (item.get(i).item_category.equals(item_category.get(code)) && item.get(i).merchantID==_merchantID)System.out.println((i+1)+" "+item.get(i));  
            }
        }
    }

    //latest order
    public void show_latestorder(int customer)
    {
        int i=0;
        while(i<Customer_list.get(customer).Order.size() && i<10)
        {           
            System.out.println((i+1)+") Order infomation: ");
            for (int y=0;y<Customer_list.get(customer).Order.get(i).orders.size();y++)
            {
                System.out.println("Bought item: "+Customer_list.get(customer).Order.get(i).orders.get(y).Name+" quantity: "+Customer_list.get(customer).Order.get(i).quantity.get(y)+" for price "+Customer_list.get(customer).Order.get(i).price.get(y)+" from Merchant "+Merchant_list.get(Customer_list.get(customer).Order.get(i).orders.get(y).merchantID-1).getName());
            }
            i++;
        }
    } 

    //add or edit orders  
    public int addtocart(Item product,int _quantity,int customer_choosed)
    {
        if (product.quantity>=_quantity)
        {
            if (product.offer.equals("25% off"))
            {
                Customer_list.get(customer_choosed).Cart.total=(product.price*0.75)*_quantity;
                Customer_list.get(customer_choosed).Cart.price.add((product.price*0.75)*_quantity);                
            }

            else if (product.offer.equals("buy one get one"))
            {                
                _quantity*=2;
                if (product.quantity<_quantity)
                {
                    System.out.println("Out of Stock");
                    return 1;
                }
                Customer_list.get(customer_choosed).Cart.total=(product.price*0.5)*_quantity;
                Customer_list.get(customer_choosed).Cart.price.add((product.price*0.5)*_quantity);
            }

            else
            {
                Customer_list.get(customer_choosed).Cart.total=product.price*_quantity;
                Customer_list.get(customer_choosed).Cart.price.add(product.price*_quantity);
            }        

            product.quantity-=_quantity;
            Customer_list.get(customer_choosed).Cart.orders.add(product);
            Customer_list.get(customer_choosed).Cart.quantity.add(_quantity);
            return 0;
        }

        else
        {
            System.out.println("Out of Stock");
            return 1;
        }
    }
    public void checkout(int customer_choosed)
    {        
        if (Customer_list.get(customer_choosed).reward_account+Customer_list.get(customer_choosed).main_account-Customer_list.get(customer_choosed).Cart.total<0)
        {
            System.out.println("Insufficient balance please try again");
            Customer_list.get(customer_choosed).Cart=new cart();
        }

        else
        {
            //commission for company for every sale
            Customer_list.get(customer_choosed).contribution+=Customer_list.get(customer_choosed).Cart.total*0.005;

            //adding comission to every merchant
            for (int i=0;i<Customer_list.get(customer_choosed).Cart.orders.size();i++)
            {
                int extra_1=(int)Merchant_list.get(Customer_list.get(customer_choosed).Cart.orders.get(i).merchantID).contribution/100;
                Merchant_list.get(Customer_list.get(customer_choosed).Cart.orders.get(i).merchantID).contribution+=((Customer_list.get(customer_choosed).Cart.price.get(i))*0.005);
                int extra_2=(int)Merchant_list.get(Customer_list.get(customer_choosed).Cart.orders.get(i).merchantID).contribution/100;
                Merchant_list.get(Customer_list.get(customer_choosed).Cart.orders.get(i).merchantID).total_itemslot+=extra_2-extra_1;
            }


            Customer_list.get(customer_choosed).main_account-=Customer_list.get(customer_choosed).Cart.total;
            if(Customer_list.get(customer_choosed).main_account<0)
            {
                Customer_list.get(customer_choosed).reward_account+=Customer_list.get(customer_choosed).main_account;
                Customer_list.get(customer_choosed).main_account=0;
            }

            Customer_list.get(customer_choosed).Order.add(Customer_list.get(customer_choosed).Cart);
            Customer_list.get(customer_choosed).Cart=new cart();
            System.out.println("Purchase successfull");
        }
        
        if (Customer_list.get(customer_choosed).Order.size()%5==0)Customer_list.get(customer_choosed).reward_account+=10;

    }
    public void clearcart(int customer_choosed)
    {
        //update the quantity which was decreased before
        for (int i=0;i<Customer_list.get(customer_choosed).Cart.orders.size();i++)
        {
            Customer_list.get(customer_choosed).Cart.orders.get(i).quantity+=Customer_list.get(customer_choosed).Cart.quantity.get(i);
        }
        Customer_list.get(customer_choosed).Cart=new cart();
    }

    //show merchannt and customer
    public void show_members()
    {
        for (int i=0;i<Merchant_list.size();i++)
        {
            System.out.println("M "+(i+1)+" "+Merchant_list.get(i).getName());
        }
        for (int i=0;i<Merchant_list.size();i++)
        {
            System.out.println("C "+(i+1)+" "+Customer_list.get(i).getName());
        }
    }
    public void detail_Merchant(int code)
    {
        System.out.println("Name: "+Merchant_list.get(code).getName()+" Address: "+Merchant_list.get(code).getAddress()+" contribution to company: "+Merchant_list.get(code).contribution);
    }
    public void detail_customer(int code)
    {
        System.out.println("Name: "+Customer_list.get(code).getName()+" Address: "+Customer_list.get(code).getAddress()+" Numbers of orders: "+Customer_list.get(code).Order.size());
    }

    //total contribution
    public void totalContribution()
    {
        double sum=0;
        for (int i=0;i<Customer_list.size();i++)
        {
            sum=sum+Customer_list.get(i).contribution;
        }
        System.out.println(sum*2);
    }
}


//class for single item
class Item
{
    String Name,offer,item_category;
    double price;
    int quantity,merchantID;

    Item(String _name,double _price,int _quantity,String _item_category,int _merchantID)
    {
        this.Name=_name;
        this.price=_price;
        this.quantity=_quantity;
        this.offer="None";
        this.item_category=_item_category;
        this.merchantID=_merchantID;
    }

    public String toString()
    {
        return this.Name+" "+this.price+" "+this.quantity+" "+this.offer+" "+this.item_category;
    }

    public void setoffer(String _offer)
    {
        this.offer=_offer;
    }

    public void edititem(double _price,int _quantity)
    {
        this.price=_price;
        this.quantity=_quantity;
    }
}

interface setorget_string
{
    public String getName();
    public void setaddress(String _address);
    public String getAddress();
}

class Merchant implements setorget_string
{
    private String name,address;
    public int total_itemslot=10,used_slot=0;
    double contribution;
    Merchant(String _name)
    {
        this.name=_name;
        contribution=0;
    }
    public String toString(){return this.name;}
    public String getName(){return this.name;}
    public void setaddress(String _address){this.address=_address;}
    public String getAddress(){return this.address;}
    
}

class Customer implements setorget_string
{
    private String name,address;
    double main_account,reward_account,contribution;
    cart Cart=new cart();
    ArrayList<cart> Order=new ArrayList<cart>();

    Customer(String _name)
    {
        this.name=_name;
        this.main_account=100;
        this.reward_account=0;
        contribution=0;
    }
    public String toString(){return this.name;}
    public String getName(){return this.name;}
    public void setaddress(String _address){this.address=_address;}
    public String getAddress(){return this.address;}
}

class cart
{
    double total=0;
    ArrayList<Item> orders=new ArrayList<Item>();
    ArrayList<Integer> quantity=new ArrayList<Integer>();
    ArrayList<Double> price=new ArrayList<Double>();
}








public class Main_ecommerce 
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));

        Ecommerce Mercury=new Ecommerce();

        int choice=0; //initisation to enter in the loop

        while(choice!=5)
        {
            System.out.println("\nWelcome to Mercury\n1) Enter as Merchant\n2) Enter as Customer\n3) See user details\n4) Company account balance\n5) Exit");
            choice=Integer.parseInt(buffer.readLine());

            //for merchant
            if (choice==1)
            {
                int option=0;

                //displaying merchant
                System.out.println("Choose merchant");
                for (int i=0;i<Mercury.size;i++)
                {
                    System.out.println((i+1)+" "+Mercury.Merchant_list.get(i));
                }
                int merchant_chosed=Integer.parseInt(buffer.readLine());

                                
                while (option!=6)
                {
                    //display chosed merchant
                    System.out.println("\nWelcome "+Mercury.Merchant_list.get(merchant_chosed-1));

                    //displaying menu for nerchant
                    System.out.println("Merchant Menu\n1) Add item\n2) Edit item\n3) Search by category\n4) Add offer\n5) Rewards won\n6) Exit");
                    option=Integer.parseInt(buffer.readLine());

                    if (option==1)
                    {
                        System.out.println("Enter item details\nitem name:");
                        String name=buffer.readLine();
                        System.out.println("item price:");
                        double price=Double.parseDouble(buffer.readLine());
                        System.out.println("item quantity:");
                        int quantity=Integer.parseInt(buffer.readLine());
                        System.out.println("item category:");
                        String category=buffer.readLine();
                        try{Mercury.additem(name,price,quantity,category,merchant_chosed);}
                        catch(Exception e){System.out.println("\nError in adding item please try again");}
                    }

                    else if (option==2)
                    {
                        System.out.println("choose item by code");
                        Mercury.showallproducts();
                        int code =Integer.parseInt(buffer.readLine());
                        System.out.println("Enter edit details\nitem price:");
                        double price=Double.parseDouble(buffer.readLine());
                        System.out.println("item quantity:");
                        int quantity=Integer.parseInt(buffer.readLine());
                        try{Mercury.edititem(code-1, price, quantity);}
                        catch(Exception e){System.out.println("Error in editing the data please try again");}
                    }

                    else if (option==3)
                    {
                        System.out.println("Choose a category");
                        Mercury.display_category();
                        Mercury.showbycategory(Integer.parseInt(buffer.readLine())-1,merchant_chosed-1);
                    }

                    else if (option==4)
                    {
                        System.out.println("choose item by code");
                        Mercury.showallproducts();
                        int code =Integer.parseInt(buffer.readLine());
                    
                        System.out.println("choose offer\n1) buy one get one\n2) 25% off");
                        int offer=Integer.parseInt(buffer.readLine());
                        Mercury.addoffer(code-1,offer-1);

                        Mercury.display(code-1);                        
                    }

                    else if (option==5)
                    {
                        System.out.println("You have been allocated : "+(Mercury.Merchant_list.get(merchant_chosed-1).total_itemslot-10)+" extra slot as reward");
                    }

                    else if (option==6)
                    {
                        continue;
                    }

                    else
                    {
                        System.out.println("Wrong choise enter again : ");
                        option=0;
                    }
                }
            }

            //for customer
            else if (choice==2)
            {
                int option=0;

                //displaying customer
                System.out.println("Choose customer");
                for (int i=0;i<Mercury.size;i++)
                {
                    System.out.println(i+1+" "+Mercury.Customer_list.get(i));
                }
                int customer_chosed=Integer.parseInt(buffer.readLine());

                while (option!=5)
                {
                    //display chosed customer
                    System.out.println("\nWelcome "+Mercury.Customer_list.get(customer_chosed-1));

                    //displaying menu for customer
                    System.out.println("Customer Menu\n1) Search item\n2) checkout cart\n3) Reward won\n4) print latest orders\n5) Exit");
                    option=Integer.parseInt(buffer.readLine());

                    if (option==1)
                    {
                        System.out.println("Choose a category");
                        Mercury.display_category();
                        System.out.println("choose item by code");
                        Mercury.showbycategory(Integer.parseInt(buffer.readLine())-1,-1);
                        System.out.println("Enter item code");
                        int code=Integer.parseInt(buffer.readLine());
                        System.out.println("Enter quantity");
                        int quantity=Integer.parseInt(buffer.readLine());
                        System.out.println("Choose method of transaction\n1) Buy item\n2) Add item to cart\n3) Exit");
                        int choose=Integer.parseInt(buffer.readLine());

                        if(choose==1)
                        {
                            if(Mercury.addtocart(Mercury.item.get(code-1), quantity, customer_chosed-1)==0)
                            {
                                Mercury.checkout(customer_chosed-1);
                            }                            
                        }
                        else if(choose==2)
                        {
                            Mercury.addtocart(Mercury.item.get(code-1), quantity, customer_chosed-1);
                        }

                        else if (choose==3)
                        {
                            Mercury.clearcart(customer_chosed-1);
                        }
                        
                        else{continue;}
                    }

                    else if (option==2)
                    {
                        Mercury.checkout(customer_chosed-1);
                    }

                    else if (option==3)
                    {
                        System.out.println("Your reward is :"+Mercury.Customer_list.get(customer_chosed-1).reward_account);
                    }

                    else if (option==4)
                    {
                        Mercury.show_latestorder(customer_chosed-1);
                    }

                    else if (option==5)
                    {
                        continue; 
                    }

                    else
                    {
                        System.out.println("Wrong choise enter again : ");
                        option=0;
                    }
                }
            }

            else if (choice==3)
            {
                Mercury.show_members();
                StringTokenizer choice_string = new StringTokenizer(buffer.readLine());
                String ch=choice_string.nextToken();
                int code=Integer.parseInt(choice_string.nextToken());
                if (ch.equals("M"))
                {
                    Mercury.detail_Merchant(code-1);
                }
                else
                {
                    Mercury.detail_customer(code-1);
                }
            }

            else if (choice==4)
            {
                Mercury.totalContribution();
            }

            else if (choice==5)
            {
                continue;
            }

            else 
            {
                System.out.println("Wrong input is given");
            }
        }
    }    
}
