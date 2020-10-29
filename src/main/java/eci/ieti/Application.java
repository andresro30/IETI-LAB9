package eci.ieti;

import eci.ieti.config.AppConfiguration;
import eci.ieti.data.CustomerRepository;
import eci.ieti.data.ProductRepository;
import eci.ieti.data.TodoRepository;
import eci.ieti.data.UserRepository;
import eci.ieti.data.model.Customer;
import eci.ieti.data.model.Product;

import eci.ieti.data.model.Todo;
import eci.ieti.data.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        customerRepository.deleteAll();

        customerRepository.save(new Customer("Alice", "Smith"));
        customerRepository.save(new Customer("Bob", "Marley"));
        customerRepository.save(new Customer("Jimmy", "Page"));
        customerRepository.save(new Customer("Freddy", "Mercury"));
        customerRepository.save(new Customer("Michael", "Jackson"));

        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        
        customerRepository.findAll().stream().forEach(System.out::println);
        System.out.println();
        
        productRepository.deleteAll();

        productRepository.save(new Product(1L, "Samsung S8", "All new mobile phone Samsung S8"));
        productRepository.save(new Product(2L, "Samsung S8 plus", "All new mobile phone Samsung S8 plus"));
        productRepository.save(new Product(3L, "Samsung S9", "All new mobile phone Samsung S9"));
        productRepository.save(new Product(4L, "Samsung S9 plus", "All new mobile phone Samsung S9 plus"));
        productRepository.save(new Product(5L, "Samsung S10", "All new mobile phone Samsung S10"));
        productRepository.save(new Product(6L, "Samsung S10 plus", "All new mobile phone Samsung S10 plus"));
        productRepository.save(new Product(7L, "Samsung S20", "All new mobile phone Samsung S20"));
        productRepository.save(new Product(8L, "Samsung S20 plus", "All new mobile phone Samsung S20 plus"));
        productRepository.save(new Product(9L, "Samsung S20 ultra", "All new mobile phone Samsung S20 ultra"));
        
        System.out.println("Paginated search of products by criteria:");
        System.out.println("-------------------------------");
        
        productRepository.findByDescriptionContaining("plus", PageRequest.of(0, 2)).stream()
        	.forEach(System.out::println);
   
        System.out.println();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MongoOperations mongoOperation = (MongoOperations) applicationContext.getBean("mongoTemplate");

        Query query = new Query();
        query.addCriteria(Criteria.where("firstName").is("Alice"));

        Customer customer = mongoOperation.findOne(query, Customer.class);

        todoRepository.deleteAll();

        System.out.println(".... Second Part...");

        todoRepository.save(new Todo("Clean the kitchen",1,new Date(),"andrew@hotmail.com","In progress"));
        todoRepository.save(new Todo("Clean the living room",3,new Date(2020,5,3),"Andrew","Ready"));
        todoRepository.save(new Todo("Clean the bathroom",5,new Date(2020,7,12),"Sara","Ready"));
        todoRepository.save(new Todo("Clean the Andrew's bedroom",5,new Date(),"andrew@hotmail.com","In progress"));
        todoRepository.save(new Todo("Clean the Sara's bedroom",5,new Date(),"sara@hotmail.com","In progress"));
        todoRepository.save(new Todo("Clean the Parent's bedroom",3,new Date(2020,2,1),"may@hotmail.com","Ready"));
        todoRepository.save(new Todo("Wash the car",3,new Date(),"william@hotmail.com","Completed"));
        todoRepository.save(new Todo("Doing lunch",3,new Date(),"william@hotmail.com","In progress"));
        todoRepository.save(new Todo("Mop the house",3,new Date(),"andrew@hotmail.com","In progress"));
        todoRepository.save(new Todo("take out the trash",1,new Date(),"may@hotmail.com","Completed"));
        todoRepository.save(new Todo("Wash the dishes",3,new Date(),"william@hotmail.com","Completed"));

        todoRepository.save(new Todo("Buy a motorcycle",5,new Date(2020,12,24),"andrew@hotmail.com","In progress"));
        todoRepository.save(new Todo("Buy a new car",3,new Date(2020,12,10),"william@hotmail.com","In progress"));
        todoRepository.save(new Todo("Buy a umbrella",2,new Date(),"may@hotmail.com","Completed"));
        todoRepository.save(new Todo("Buy a new shoes like aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",5,new Date(2020,10,15),"may@hotmail.com","Completed"));
        todoRepository.save(new Todo("Going to doctor",3,new Date(),"may@hotmail.com","In progress"));
        todoRepository.save(new Todo("Buy a new clothes",3,new Date(2020,11,12),"may@hotmail.com","In progress"));
        todoRepository.save(new Todo("Going to the hairdresser",3,new Date(),"may@hotmail.com","Completed"));
        todoRepository.save(new Todo("Buy a new books",2,new Date(),"sara@hotmail.com","Completed"));
        todoRepository.save(new Todo("Take pictures",3,new Date(),"sara@hotmail.com","Completed"));
        todoRepository.save(new Todo("Finish the university's works",3,new Date(),"sara@hotmail.com","Completed"));
        todoRepository.save(new Todo("Wash the dog",3,new Date(),"sara@hotmail.com","Completed"));
        todoRepository.save(new Todo("Wash the cat",3,new Date(),"sara@hotmail.com","Completed"));
        todoRepository.save(new Todo("Wash the duck",3,new Date(),"andrew@hotmail.com","Completed"));
        todoRepository.save(new Todo("Buy a new basketball ball",3,new Date(2020,11,12),"andrew@hotmail.com","Completed"));
        todoRepository.save(new Todo("Finish the Ieti lab",3,new Date(),"andrew@hotmail.com","In progress"));


        //User data
        userRepository.deleteAll();

        userRepository.save(new User("Andrew","andrew@hotmail.com"));
        userRepository.save(new User("Sara","sara@hotmail.com"));
        userRepository.save(new User("May","may@hotmail.com"));
        userRepository.save(new User("William","william@hotmail.com"));

        for(int i=1;i<7;i++){
            String user = "user"+String.valueOf(i);
            userRepository.save(new User(user,user+"@hotmail.com"));
        }

        //Date expired query
        Query queryDate = new Query();
        queryDate.addCriteria(Criteria.where("dueDate").lte(new Date(2020,10,29)));
        List<Todo>  expiredTodos = mongoOperation.find(queryDate, Todo.class);
        System.out.println("Date expired query");
        System.out.println("Size: "+expiredTodos.size());
        expiredTodos.stream().forEach(System.out::println);

        //Priority eq 5 query
        Query queryPriority = new Query();
        queryPriority.addCriteria(Criteria.where("responsible").is("andrew@hotmail.com").and("priority").is(5));
        List<Todo>  priorityTodos = mongoOperation.find(queryPriority, Todo.class);
        System.out.println("Priority query");
        System.out.println("Size: "+priorityTodos.size());
        priorityTodos.stream().forEach(System.out::println);

        //Size description greater than 30
        Query queryDescription = new Query();
        queryDescription.addCriteria(Criteria.where("description").regex("[a-z,A-Z,0-9,' ']{30,}"));
        List<Todo>  descriptionTodos = mongoOperation.find(queryDescription, Todo.class);
        System.out.println("Description query");
        System.out.println("Size: "+descriptionTodos.size());
        descriptionTodos.stream().forEach(System.out::println);
    }

}