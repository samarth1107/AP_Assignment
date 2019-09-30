/*
Samarth Chauhan 
2018410
CSB
*/

import java.io.*;
import java.util.*;
import java.util.Arrays;

/*
0.nothing (white tile)
1.Snake
2.Cricket
3.Vulture
4.Trampoline
*/

final class Game
{
    private final int[] track,track_obstacle,track_obstacle_penalty,track_obstacle_count;
    private final String player_name;
    Random rand=new Random();

    Game(int _track,String _name)
    {
        List<Integer> lst = new ArrayList<Integer>();
        track=new int[_track];
        track_obstacle=new int[5];
        track_obstacle_penalty=new int[5];
        track_obstacle_count=new int[5];
        this.player_name=_name;
        
        //from here track is set from here   
        System.out.println("Setting up the race track");
        int seek=0,start=0,total;
        for (int i=1;i<=4;i++)
        {
            total=rand.nextInt(_track/5)+1;
            track_obstacle[i]=total;
            track_obstacle_penalty[i]=total+rand.nextInt(_track/10);//to create random penalty
            track_obstacle_count[i]=0;
            while(seek<start+total)
            {
                lst.add(i);
                seek++;
            }
            start=seek;
        }
        track_obstacle[0]=_track-seek;
        track_obstacle_penalty[0]=0;
        track_obstacle_count[0]=0;        
        //set track end here  
        
        //from here array is shuffled
        for (int i=0;i<track_obstacle[0]-2;i++)lst.add(0);//to add remaining 0
        Collections.shuffle(lst);
        lst.add(0);//to add 0 at last and start of the array
        lst.add(0,0);
        for (int i=0;i<_track;i++)track[i]=lst.get(i);
        //System.out.println(Arrays.toString(track));
        this.display_penalty();
    }

    private void display_penalty()
    {
        System.out.printf("\nDanger: There are %d, %d, %d numbers of Snakes, Cricket, and Vultures respectively on your track!",this.track_obstacle[1],this.track_obstacle[2],this.track_obstacle[3]);
        System.out.printf("\nDanger: Each Snake, Cricket, and Vultures can throw you back by %d, %d, %d number of Tiles respectively!",this.track_obstacle_penalty[1],this.track_obstacle_penalty[2],this.track_obstacle_penalty[3]);
        System.out.printf("\nGood News: There are %d number of Trampolines on your track!",this.track_obstacle[4]);
        System.out.printf("\nGood News: Each Trampoline can help you advance by %d number of Tiles",this.track_obstacle_penalty[4]);
    }

    public void startgame()
    {
        System.out.println("Game Started ======================>");
        int loop=0,roll,current_pos=-1;
        while(current_pos<this.track.length-1)
        {
            loop++;
            roll=rand.nextInt(6)+1;                        
            
            //if player is on starting position
            if (current_pos==-1)
            {
                System.out.printf("\n>>[Roll-%d]: %s rolled %d at Tite-%d, ",loop,this.player_name,roll,current_pos+2);
                if(roll==6)
                {
                    System.out.printf("You are out of the cage! You get a free roll");
                    current_pos=0;
                }
                else System.out.printf("OOPs you need 6 to start ");
                continue;
            }

            //situation other than start;
            System.out.printf("\n>>[Roll-%d]: %s rolled %d at Tite-%d, ",loop,this.player_name,roll,current_pos+1);
            current_pos+=roll; 
            current_pos=this.display_tile_detail(current_pos,roll); //to get updated value of current pos after penatly
            /*catch (SnakeBiteException e){System.out.println(e.getMessage());}
            catch (CricketBiteException e){System.out.println(e.getMessage());}
            catch (VultureBiteException e){System.out.println(e.getMessage());}
            catch (TrampolineException e){System.out.println(e.getMessage());}*/

            try{if (current_pos==track.length-1) throw new GameWinnerException(this.player_name,loop,this.track_obstacle_penalty[1],this.track_obstacle_penalty[2],this.track_obstacle_penalty[3],this.track_obstacle_penalty[4]);} 
            catch (GameWinnerException e) {System.out.println(e.getMessage());}
        }
    }

    private int display_tile_detail(int current_pos,int roll) 
    {

        if (current_pos>this.track.length-1) //case when current position will be greater than total length
        {
            current_pos-=roll;
            System.out.printf("landed on Tile %d",current_pos+1);   
            return current_pos;
        } 

        System.out.printf("landed on Tile %d",current_pos+1);            
        System.out.printf("\n>>\tTrying to shake the Tile-%d",current_pos+1); 
        
        if (this.track[current_pos]==1) //snake
        {            
            current_pos-=this.track_obstacle_penalty[1];
            this.track_obstacle_count[1]++;
            this.throwExceptions(1, this.track_obstacle_penalty[1]);
        }
        else if (this.track[current_pos]==2) //cricket
        {            
            current_pos-=this.track_obstacle_penalty[2];
            this.track_obstacle_count[2]++;
            this.throwExceptions(2, this.track_obstacle_penalty[2]);
        }
        else if (this.track[current_pos]==3) //vulture
        {
            current_pos-=this.track_obstacle_penalty[3];
            this.track_obstacle_count[3]++;
            this.throwExceptions(3, this.track_obstacle_penalty[3]);
        }
        else if (this.track[current_pos]==4)//trampoline
        {
            current_pos+=this.track_obstacle_penalty[4];
            this.track_obstacle_count[4]++;

            if (current_pos>track.length-1)
            {
                current_pos-=this.track_obstacle_penalty[4];
                this.throwExceptions(4, this.track_obstacle_penalty[4]);
                System.out.println("Power of trampoline will not be used as it will throw you out of the track");
            }
            this.throwExceptions(4, this.track_obstacle_penalty[4]);         
        }        
        else 
        {
            System.out.printf("\n>>\tI am a White tile!");
        }


        //if current pos gets negative
        if (current_pos<0)
        {
            current_pos=-1;
            System.out.printf("\n>>\t%s moved to Tile 1 as it can’t go back further",this.player_name);
        }        
        else System.out.printf("\n>>\t%s moved to Tile-%d",this.player_name,current_pos+1);

        return current_pos;
    }

    private void throwExceptions(int i,int argument)
    {
        if (i==0){
            return;
        }
        else if(i==1){
            try{throw new SnakeBiteException(argument);}
            catch(SnakeBiteException e){System.out.println(e.getMessage());}
        }
        else if(i==2){
            try{throw new CricketBiteException(argument);}
            catch(CricketBiteException e){System.out.println(e.getMessage());}
        }
        else if(i==3){
            try{throw new VultureBiteException(argument);}
            catch(VultureBiteException e){System.out.println(e.getMessage());}
        }
        else if(i==4){
            try{throw new TrampolineException(argument);}
            catch(TrampolineException e){System.out.println(e.getMessage());}
        }
        else{return;}
    }
}


class SnakeBiteException extends Exception
{
    public SnakeBiteException(int back)
    {
        super("\n>>\tHiss !!! I am a Snake, you go back "+back+" tiles!");
    }
}
class CricketBiteException extends Exception
{
    public CricketBiteException(int back)
    {
        super("\n>>\tChirp !!! I am a Cricket, you go back "+back+" tiles!");
    }
}
class VultureBiteException extends Exception
{
    public VultureBiteException(int back)
    {
        super("\n>>\tYapping !!! I am a Vulture, you go back "+back+" tiles!");
    }
}
class TrampolineException extends Exception
{
    public TrampolineException(int back)
    {
        super("\n>>\tPingPong! I am a Trampoline, you advance "+back);
    }
    public TrampolineException()
    {
        super("\nAs you cannot go beyond track so bonus of white title is not used here");
    }
}
class GameWinnerException extends Exception
{
    public GameWinnerException(String player_name,int loop,int track1,int track2,int track3,int track4)
    {
        super("\n\n>>\t\t"+player_name+" wins the race in "+loop+" rolls!\n>>\t\tTotal snake bites = "+track1+"\n>>\t\tTotal vulture bites = "+track2+"\n>>\t\tTotal cricket bites = "+track3+"\n>>\t\tTotal trampolines = "+track4);   
    }
}




class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        int track=0,Max_length=1000;
        String name="Null";       
            
        while(true) //to take track length
        {
            System.out.println("Enter total number of tiles on the race track (length)");    
            try {track=Integer.parseInt(buffer.readLine());}
            catch (NumberFormatException e){System.out.println("Error: Please enter number here");}
            catch (Exception e){System.out.println("Error: Please enter number here");}
            if (track<10 && track>Max_length)System.out.println("Please enter track tiles more than 10 and less then "+Max_length);
            else if (track>=10 && track<=Max_length) break;
            else continue;
        }           
        
        while(true) //to take name of player
        {
            System.out.println("Enter the Player Name");  
            try 
            {
                name=buffer.readLine();      
                if (name.matches("[a-zA-Z]+")==false)
                {
                    System.out.println("Error: Enter name in characters only !!!!");
                    continue;
                }
                try 
                {
                    Integer.parseInt(name);
                    System.out.println("Error: Enter name in characters only !!!!");
                    continue;
                }
                catch(Exception e) {}
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("Error:Please enter name in characters not in numbers");
                continue;
            }
            catch (Exception e)
            {
                System.out.println("Error occured please try again");
                continue;
            }
            
            break;
        } 
        
        Game game=new Game(track,name); //game obj creation

        System.out.printf("\n>>Starting the game with %s at Tile-1\n>>Control transferred to Computer for rolling the Dice for Josh\n>>Hit enter to start the game",name);
        
        while (true) //readline for confirmation from user to start game only enter will work
        {
            if (buffer.readLine().equals("")==true)
            {
                game.startgame(); 
                break;
            }
            System.out.println("Error: press enter to start the game !!!!");
        }
        
        System.out.println("Game finished !!!!!");
    }
}