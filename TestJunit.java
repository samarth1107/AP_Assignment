import org.junit.*;

public class TestJunit 
{   
    @Test(expected = SnakeBiteException.class)
    public void test_snakebite()throws SnakeBiteException
    {   
        Game game = new Game(10,"Test");
        try 
        {
           game.junit_test(1,1);
        }
        catch(SnakeBiteException e)
        {
           throw e;
        }
        catch(Exception e)
        {
           System.out.println("Failed");
        }
    }
    
    @Test(expected = CricketBiteException.class)
    public void test_cricketbite()throws CricketBiteException
    {   
      Game game = new Game(10,"Test");
      try 
      {
         game.junit_test(2,1);
      }
      catch(SnakeBiteException e)
      {
         throw e;
      }
      catch(Exception e)
      {
         System.out.println("Failed");
      }
    }
    
    @Test(expected = VultureBiteException.class)
    public void test_vulturebite()throws VultureBiteException
    {  
      Game game = new Game(10,"Test");
      try 
      {
         game.junit_test(3,1);
      }
      catch(SnakeBiteException e)
      {
         throw e;
      }
      catch(Exception e)
      {
         System.out.println("Failed");
      } 

    }
    
    @Test(expected = TrampolineException.class)
    public void test_trampoline()throws TrampolineException
    {   
      Game game = new Game(10,"Test");
      try 
      {
         game.junit_test(4,1);
      }
      catch(SnakeBiteException e)
      {
         throw e;
      }
      catch(Exception e)
      {
         System.out.println("Failed");
      }

    }
    
    @Test(expected = GameWinnerException.class)
    public void test_gamewinner()throws GameWinnerException
    {  
      Game game = new Game(10,"Test");
      try 
      {
         game.junit_test(5,1);
      }
      catch(SnakeBiteException e)
      {
         throw e;
      }
      catch(Exception e)
      {
         System.out.println("Failed");
      }

    }

    @Test(expected = -1)
    public void test_savefile()
    {
       SaveGame save = new SaveGame();
       try 
       {
          if(save.Save(new Game(10,"Test"),"Test")==-1)throw Exception;
          throw -1;
       }
       catch (Exception e)
       {
          System.out.println("Failed");
       }
    }

    @Test(expected = Game)
    public void test_resumefile()
    {
       resumeGame save = new resumeGame();
       Game game = new Game(10,"Test");
       try 
       {
          if(!save.resume("Test").equals(game))throw Exception;
          throw game;
       }
       catch (Exception e)
       {
          System.out.println("Failed");
       }
    }
 
 }