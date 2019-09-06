import java.io.*;
import java.nio.file.Path;
import java.util.*;

class User
{
    BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
    private ArrayList<Game> Users=new ArrayList<>();

    public void menu() throws IOException
    {
        try{
            System.out.println("Welcome to ArchLegends\nChoose your option\n1) New User\n2) Existing User\n3) Exit");
            int choice=Integer.parseInt(buffer.readLine());       

            if (choice==1)this.createuser();
            else if(choice==2)this.finduser();
            else if (choice==3)return;

            else 
            {
                System.out.println("Wrong input try again");
                this.menu();
            }
        }
        catch(Exception e){System.out.println("Error Occured");}
    }

    public void createuser() throws IOException
    {
        try{
            System.out.println("Enter Username");
            String name=buffer.readLine();
            System.out.println("Choose a Hero\n1) Warrior\n2) Thief\n3) Mage\n4) Healer");
            int choice=Integer.parseInt(buffer.readLine());
            Users.add(new Game(name, choice));
            this.menu();
        }
        catch(Exception e){System.out.println("Error Occured");}        
    }

    public void finduser() throws IOException
    {
        try{
            System.out.println("Enter Username");
            String name = buffer.readLine();
            int flag=0;
            for (Game username:Users)
            {
                if (username.getName().equals(name))
                {
                    System.out.println("User Found... logging in\nWelcome "+name);
                    flag=1;
                    username.StartGame();
                }
            }

            if (flag==0)
            {
                System.out.println("No user found try again");
                this.menu();
            }
        }

        catch(Exception e){System.out.println("Error Occured");}
    }
}


class Game
{
    private final String name;
    private Hero Player;
    private Monster Enemy;
    generate_path[] Random_path=new generate_path[3];

    Game(String _name,int hero)
    {
        this.name=_name;
        if (hero==1)Player=new Warrior();
        else if(hero==2)Player=new Thief();
        else if (hero==3)Player=new Mage();
        else if (hero==4)Player=new Healer();
        else 
        {
            System.out.println("Type hero between 1-4");
            return;
        }

        System.out.println("User Creation done. Username: "+_name+". Hero type: "+Player.getClass().getName()+". Log in to play the game . Exiting");        

        generate_path(); //to generate random path for every unique user
    }

    public String toString()
    {
        return "This is game class";
    }

    public String getName(){return name;}

    public void StartGame() throws IOException
    {
        //System.out.println("All paths generated");
        int i=0;
        while (i<5)
        {   
            i++;
            Show_Paths(i);
            if (FindLevel()==1)i--; //this will find level and give options to play the level            
        }
    }

    public int FindLevel() throws IOException
    {
        int instrution;
        if (Player.getXP()==0)
        {
            if (Take_Path(1)==-1){return -1;}
            Fight(1);
        }
        else if (Player.getXP()==20)
        {
            instrution=Take_Path(2);
            if (instrution==-1){return -1;}
            else if(instrution==1)
            {
                Show_Paths(1);
                Take_Path(1);
                Fight(1);
                return 1;
            }
            Fight(2);
        }
        else if (Player.getXP()==40)
        {
            instrution=Take_Path(3);
            if (instrution==-1){return -1;}
            else if(instrution==1)
            {
                Show_Paths(2);
                Take_Path(2);
                Fight(2);      
                return 1;          
            }
            Fight(3);
        }
        else
        {
            instrution=Take_Path(4);
            if (instrution==-1){return -1;}
            else if(instrution==1)
            {                
                Show_Paths(3);
                Take_Path(3);
                Fight(3); 
                return 1;
            }
            Fight(4);  
        }
        return 0;
    }

    public void generate_path()
    {
        Random_path[0]=new generate_path(1,1);
        Random_path[1]=new generate_path(2,2);
        Random_path[2]=new generate_path(3,3);    
    }

    public void Show_Paths(int Path)
    {
        if (Path==1)
        {
            System.out.println("\nYou are at the starting location. Choose path:");
            System.out.println("1) Go to Location "+Random_path[Path-1].show_path(0));
            System.out.println("2) Go to Location "+Random_path[Path-1].show_path(1));
            System.out.println("3) Go to Location "+Random_path[Path-1].show_path(2));
            System.out.println("Enter -1 to exit");
        }

        else if (Path==2 || Path==3)
        {
            System.out.println("\nLevel Up: level:"+Path);
            System.out.println("Fight won proceed to the next location.");
            System.out.println("1) Go to Location "+Random_path[Path-1].show_path(0));
            System.out.println("2) Go to Location "+Random_path[Path-1].show_path(1));
            System.out.println("3) Go to Location "+Random_path[Path-1].show_path(2));
            System.out.println("4) Go Back ");
            System.out.println("Enter -1 to exit");
        }

        else
        {
            System.out.println("\nLevel Up: level:4 (Last level)");
            System.out.println("Now you have to fight with LionFang");
            System.out.println("1) To Start Fight ");
            System.out.println("4) Go Back ");
            System.out.println("Enter -1 to exit");
        }        
    }

    public int Take_Path(int Path) throws IOException
    {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        int choice=0;

        if (Path==4)  //exception for last fight
        {
            try{choice=Integer.parseInt(buffer.readLine());}
            catch(Exception e){System.out.println("Wrong input type");} 
            System.out.println("Moving to the kingdom of Thunderforge");
            if (choice==1)return 0;
            else if (choice==4)
            {
                System.out.println("Going Back <<<<<<");
                return 1;
            }

            else if (choice==-1){System.out.println("Exiting.");}
            else {System.out.println("Wrong input is selected");}
            return -1;

        }


        try{choice=Integer.parseInt(buffer.readLine());}
        catch(Exception e){System.out.println("Wrong input type");}        

        if (choice==1 || choice==2 || choice==3)
        {            
            System.out.println("Moving to location "+Random_path[Path-1].show_path(choice-1));
            return 0;
        }
        else if (choice==4)
        {
            System.out.println("Going Back <<<<<<");
            return 1;
        }

        else if (choice==-1)
        {
            System.out.println("Exiting.");
            System.exit(0);
        }
        else {System.out.println("Wrong input is selected");}
        return -1;
    }

    public void Fight(int Step) throws IOException
    {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        set_enemy(Step);  //in this both line Step 1 == level 1
        Player.setHP();
        int move=0;

        while (Player.getHP()>0 && Enemy.getHP()>0)
        {
            Player.increase_move();

            System.out.println("Choose move:\n1) Attack\n2) Defense");
            if (Player.getTotalmove()>3){System.out.println("3) Special Attack");}
            
            try{move=Integer.parseInt(buffer.readLine());}
            catch(Exception e){System.out.println("Invalid type");}

            if (move==1)
            {
                Player.Attack(Enemy);
                ShowHPs(); // to show HP of both 
                Enemy.Attack(Player);
                ShowHPs(); // to show HP of both 
            }

            else if (move==2)
            {
                Player.Defend(Enemy);
                ShowHPs(); // to show HP of both 
                Enemy.AttackinDefance(Player);
                ShowHPs(); // to show HP of both 
            }

            else if (move==3 && Player.getTotalmove()>3)
            {
                Player.SpecialAttack_Start(Enemy);
                Player.resetTotalmove();
                continue;
            }

            else 
            {
                System.out.println("Wrong input is selected");
                continue;
            }

            if (Player.Special_move>-1){Player.decrease_Special_move();}//to decrease the special power if activated
            
            if (Player.Special_move==0)
            {
                Player.SpecialAttack_End(Enemy);
                Player.resetTotalmove();
            }//to deactivate the special power if activated
            
            if (Enemy.getHP()==0 && Step==4)
            {
                System.out.println("\n\nLionfang killed!");
                System.out.println("You have won the game !!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("------------------------------------------------Winner winner paneer dinner\n\n");
                System.exit(0);
                break;
            }

            if (Enemy.getHP()==0)
            {
                System.out.println("\n\nMonster killed!");
                Player.setXP(Random_path[Step-1].show_monster()*20);
                System.out.println(Random_path[Step-1].show_monster()*20+" XP awarded");
                break;
            }

            if (Player.getHP()==0)
            {
                System.out.println("Game Lost!\nTry again");
                break;
            }
        }
        Player.resetTotalmove();
        Player.reset_Special_move();
    }

    public void set_enemy(int level)
    {        
        if (level==4) //special last case
        {
            Enemy=new Lionfang();
            return;
        }
        else if (Random_path[level-1].show_monster()==1){Enemy=new Gobin();}
        else if (Random_path[level-1].show_monster()==2){Enemy=new Zombies();}
        else if (Random_path[level-1].show_monster()==3){Enemy=new Fiends();}
        System.out.println("Fight Stated. You are fighting level "+Random_path[level-1].show_monster()+" Monster.");
    }

    public void ShowHPs()
    {
        System.out.println("Your Hp: "+Player.getHP()+"/"+Player.getMaxHP()+" Monsters Hp: "+Enemy.getHP()+"/"+Enemy.getMaxHP());
    }
}



class generate_path{
    private final int Monster_level;
    private final int[] path;

    generate_path(int path_no,int _monster_level)
    {
        path_no-=1;
        path=new Random().ints(15*path_no,15*(path_no+1)).distinct().limit(3).toArray();
        Arrays.sort(path);
        this.Monster_level=_monster_level;
    }

    public void show_path()
    {
        System.out.println("Path is: "+this.path[0]+" "+this.path[1]+" "+this.path[2]+" and monster level is :"+this.Monster_level);
    }

    public int show_path(int pos){return path[pos];}

    public int show_monster(){return Monster_level;}

}


abstract class Person
{
    protected int HP,MaxHP;
    abstract public void Attack(Person c);
    public void reduceHP(int damage)
    {
        this.HP-=damage;
        if (this.HP<0)this.HP=0;
    }
    public int getHP(){return this.HP;}
    public int getMaxHP(){return this.MaxHP;}
}

abstract class Hero extends Person
{
    protected int XP=0,damage,DefandHP,total_move=0,Special_move=-1;
    @Override
    public void reduceHP(int damage)
    {
        this.HP-=damage;
        if (this.HP<0)this.HP=0;
    }
    public void increase_move()
    {
        this.total_move++;
        if (this.total_move>4)this.total_move=4;
    }
    public void decrease_move(){this.total_move--;}
    public int getTotalmove(){return this.total_move;}
    public void resetTotalmove(){this.total_move=0;}
    public int getSpecial_move(){return this.Special_move;}
    public void decrease_Special_move(){this.Special_move--;}
    public void reset_Special_move(){this.Special_move=-1;}
    public void setHP()
    {
        if (this.XP==0){
            this.HP=100;
            this.MaxHP=100;
        }
        else if(this.XP==20){
            this.HP=150;
            this.MaxHP=150;
        }
        else if(this.XP==40){
            this.HP=200;
            this.MaxHP=200;
        }
        else {
            this.HP=250;
            this.MaxHP=250;
        }
    }
    public int getXP(){return this.XP;}
    public void setXP(int _XP){this.XP=_XP;}
    public int getDefese(){return this.DefandHP;}
    @Override
    public void Attack(Person Enemy)
    {   
        System.out.println("You choose to attack.\nYou attacked and inflicted "+this.damage+" damage to the monster.");
        Enemy.reduceHP(this.damage);
    }
    public void Defend(Monster Enemy)
    {
        System.out.println("You choose to defend\nMonster attack reduced by"+this.DefandHP);
    }
    abstract public void SpecialAttack_Start(Monster Enemy);
    abstract public void SpecialAttack_End(Monster Enemy);
}
class Warrior extends Hero{
    Warrior()
    {
        this.damage=10;
        this.DefandHP=3;
    }
    @Override
    public void SpecialAttack_Start(Monster Enemy)
    {
        System.out.println("Special power activated\nNow Perform special attack");
        this.damage+=5;
        this.DefandHP+=5;
        this.Special_move=3;
    }
    @Override
    public void SpecialAttack_End(Monster Enemy)
    {
        System.out.println("Special power deactivated."); 
        this.damage-=5;
        this.DefandHP-=5;
    }
}
class Mage extends Hero{
    private int EnemyDamage;
    Mage()
    {
        this.damage=5;
        this.DefandHP=5;
    }
    @Override
    public void SpecialAttack_Start(Monster Enemy)
    {
        System.out.println("Special power activated\nNow Perform special attack");
        this.EnemyDamage=(int)(Enemy.getHP()*0.05);
        Enemy.reduceHP(this.EnemyDamage);
        this.Special_move=3;
    }
    @Override
    public void SpecialAttack_End(Monster Enemy)
    {
        System.out.println("Special power deactivated.");
        Enemy.reduceHP(-this.EnemyDamage); //upon subtracting it add the health      
    }
}
class Thief extends Hero{
    Thief()
    {
        this.damage=6;
        this.DefandHP=4;
    }
    @Override
    public void SpecialAttack_Start(Monster Enemy)
    {        
        System.out.println("Special power activated\nPerforming special attack\nYou have stolen "+Math.round(Enemy.getHP()*0.3)+" Hp from the monster!");
        Enemy.reduceHP((int)Math.round(Enemy.getHP()*0.3)); 
        this.Special_move=1;
    }
    @Override
    public void SpecialAttack_End(Monster Enemy)
    {
        System.out.println("Special power deactivated.");
    }
}
class Healer extends Hero{
    private int OwnHealth;
    Healer()
    {
        this.damage=4;
        this.DefandHP=8;
    }
    @Override
    public void SpecialAttack_Start(Monster Enemy)
    {
        System.out.println("Special power activated\nNow Perform special attack");
        this.OwnHealth=(int)(this.HP*0.05);
        this.HP+=this.OwnHealth;
        this.Special_move=3;
    }
    @Override
    public void SpecialAttack_End(Monster Enemy)
    {
        System.out.println("Special power deactivated.");
        this.HP-=this.OwnHealth;
    }
}


abstract class Monster extends Person
{
    Random random=new Random();
    protected int nextAttack;
    @Override
    public void Attack(Person Player)
    {
        int damage=(int)Math.round((Player.getHP()/8)+random.nextGaussian()*(Player.getHP()/8))-1;
        if (damage<0)damage=0;
        Player.reduceHP(damage);
        System.out.println("The monster attacked and inflicted "+damage+" damage to you.");
    }
    public void AttackinDefance(Hero Player)
    {
        int currAttack=(int)Math.round((Player.getHP()/8)+random.nextGaussian()*(Player.getHP()/8))-1-Player.getDefese();
        if (currAttack>0)
        {
            Player.reduceHP(currAttack);
            System.out.println("The monster attacked and inflicted "+currAttack+" damage to you.");
        }
        else
        {
            System.out.println("The monster attacked and inflicted "+0+" damage to you.");
        }
    }
    public void reset()
    {
        this.HP=this.MaxHP;
    }
}
class Gobin extends Monster{
    Gobin()
    {
        this.HP=100;
        this.MaxHP=100;
    }
}
class Zombies extends Monster{
    Zombies()
    {
        this.HP=150;
        this.MaxHP=150;
    }
}
class Fiends extends Monster{
    Fiends()
    {
        this.HP=200;
        this.MaxHP=200;
    }
}
class Lionfang extends Monster{
    Lionfang()
    {
        this.HP=250;
        this.MaxHP=250;
    }
    @Override
    public void Attack(Person Player)
    {
        Random probablity=new Random();
        if ((probablity.nextInt(10)+1)==1)// to show 1/10 probablity
        {
            int damage=(int)(Player.getHP()*0.5);
            Player.reduceHP(damage);
            System.out.println("The LionFang attacked you with his special power");
            System.out.println("The LionFang attacked and inflicted "+damage+" damage to you.");
        }
        else
        {
            int damage=(int)((Player.getHP()/4)+random.nextGaussian()*(Player.getHP()/12));
            Player.reduceHP(damage);
            System.out.println("The LionFang attacked and inflicted "+damage+" damage to you.");
        }
    }
}


class Main
{
    public static void main(String[] args) throws IOException
    {           
        User user = new User();
        try{user.menu();}
        catch(Exception e){System.out.println("Wrong input type");}
        System.out.println("\n\n\nGame is over now :");
    }
}
