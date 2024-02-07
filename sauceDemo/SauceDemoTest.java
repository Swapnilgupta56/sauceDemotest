package sauceDemo;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class SauceDemoTest {
	
	
		WebDriver driver = new ChromeDriver();
		
		
		@Test(priority=1)
		void open_loginpage() {	
			
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			driver.get("https://www.saucedemo.com/");	
			System.out.println("Swag Labs launched");		
		}
		
		@Test(priority=2, dataProvider="dp")
		public void login_with_invalid_credentials(String username, String password) throws Exception  {
				
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				WebElement username_field = driver.findElement(By.id("user-name"));
				username_field.clear();
				username_field.sendKeys(username);	
				Thread.sleep(1000);
				WebElement password_field = driver.findElement(By.id("password"));
				password_field.clear();
				password_field.sendKeys(password);		
				System.out.println("Entered UserName and Password");
				Thread.sleep(1000);
				driver.findElement(By.id("login-button")).click();
				System.out.println("Login Button clicked");
				try  {
				WebElement error_msgBox = driver.findElement(By.xpath("//h3[@data-test='error']"));
				System.out.println("Error Msg displayed"+error_msgBox.getText());
				Assert.assertTrue(error_msgBox.isDisplayed()); 
				}
				catch (Error e)   {
					System.out.println("Error msg not displayed");
				}
				System.out.println("Login Successfull");
				
		}

		
		@Test(priority=3)
		public void login_with_valid_credentials()  throws Exception{ 
			
			    
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			
			driver.findElement(By.id("user-name")).clear();
			driver.findElement(By.id("user-name")).sendKeys("standard_user");
			Thread.sleep(1000);		
			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys("secret_sauce");   
			System.out.println("Entered UserName and Password");
			Thread.sleep(1000);
			driver.findElement(By.id("login-button")).click();
			System.out.println("Login Button clicked");
			try  {
			WebElement login_menuOption = driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']"));
			Assert.assertTrue(login_menuOption.isDisplayed()); //verifying if menu button available after login
			System.out.println("Login Successfull"); 
			}
			catch (Error e)   {
				Assert.assertTrue(false);
				System.out.println("Login Failed");
			}
			 
		}

		@Test(priority=4)
		public void select_random_items()  throws Exception{
			List<WebElement> elementList = driver.findElements(By.xpath("//div[@class='inventory_list']/div"));		
			 Random r = new Random();
	         Set<Integer> s = new HashSet<Integer>(); 
	         int items = 3;  
	       
			System.out.println("Total List Of Items present are " +elementList.size());
			for(int i=0;i<items;i++ )
			{
				 while(true) {
					 int num = r.nextInt(elementList.size());
					 if (s.contains(num) == false) {
		                    s.add(num);
		                    System.out.println(num);
		                    WebElement button =	driver.findElement(By.xpath("//a[@id='item_" +num + "_title_link']/../../div/button"));
		        			if(button.isDisplayed())
		        			{
		        				button.click();
		        				WebElement selected= driver.findElement(By.xpath("//a[@id='item_" +num + "_title_link']/div"));
		        				System.out.println("Selected Item from Cart List " +selected.getText());
		        			}
		        			else
		        			{
		        			System.out.println("Add to Cart Button Not displayed");
		        			}
		                    break;
		                }
					 else
					 {
						 System.out.println("This Cart Already Selected" +num);
					 }
				 }
			}
			
			driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();
			List<WebElement> elementList1 = driver.findElements(By.xpath("//div[@class='cart_item']"));	
			Assert.assertEquals(items,elementList1.size());
			System.out.println("Passed");
			
			driver.quit();
				
		}	
		
		  @DataProvider(name="dp")
	      String [][] usernames_passwords()
	      {
	    	  String[][] data= { {"standard_user", "secret"},
	    			  {"standard_user", "secret_sauce123"},
	    			  {"problem_user", "secret@sauce"},
	    			  {"performance_glitch_user", "123secret_sauce"},
	    			  {"standard_user", "secret@_sauce"},
	    			  {"visual_user", "12345"}
					 };
	    	  
	    	  return data;
	      }
		
}
	


