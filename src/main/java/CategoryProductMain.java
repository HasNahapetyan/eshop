import manager.CategoryManager;
import manager.ProductManager;
import model.Category;
import model.Product;

import java.util.List;
import java.util.Scanner;

public class CategoryProductMain {
    private static Scanner scanner = new Scanner(System.in);

    private static CategoryManager  categoryManager = new CategoryManager();
    private static ProductManager productManager = new ProductManager();

    public static void main(String[] args) {
        boolean isRun = true;

        while (isRun) {
            System.out.println("Please input 0 for exit");
            System.out.println("Please input 1 for add Category");
            System.out.println("Please input 2 for add Product");
            System.out.println("Please input 3 for update Category by id");
            System.out.println("Please input 4 for update Product by id");
            System.out.println("Please input 5 for print sum of products");
            System.out.println("Please input 6 for print max of price product");
            System.out.println("Please input 7 for print min of price product");
            System.out.println("Please input 8 for print avg of price product");
            String command = scanner.nextLine();
            switch (command) {
                case "0":
                    isRun = false;
                    break;
                case "1":
                    addCategory();
                    break;
                case "2":
                    addProduct();
                    break;
                case "3":
                    updateCategoryById();
                    break;
                case "4":
                    updateProductById();
                    break;
                case "5":
                    System.out.println(productManager.sumOfProducts());
                    break;
                case "6":
                    System.out.println(productManager.maxOfPriceProduct());
                    break;
                case "7":
                    System.out.println(productManager.minOfPriceProduct());
                    break;
                case "8":
                    System.out.println(productManager.avgOfPriceProduct());
                    break;
                default:
                    System.out.println("Invalid Command");
            }
        }
    }

    private static void addCategory() {
        System.out.println("Please input category name");
        String categoryStr = scanner.nextLine();
        String[] categoryData = categoryStr.split(",");
        Category category = new Category();
        category.setName(categoryData[0]);
        categoryManager.save(category);
    }

    private static void addProduct() {
        List<Category> all = categoryManager.getAll();
        for (Category category : all) {
            System.out.println(category);
        }
        System.out.println("Please choose category id");
        int id = Integer.parseInt(scanner.nextLine());
        Category category = categoryManager.getById(id);
        if (category != null) {
            System.out.println("Please input product name,description,price,quantity");
            String productStr = scanner.nextLine();
            String[] productData = productStr.split(",");

            Product product = new Product();
            product.setCategory(category);
            product.setName(productData[0]);
            product.setDescription(productData[1]);
            product.setPrice(Integer.parseInt(productData[2]));
            product.setQuantity(Integer.parseInt(productData[3]));
            productManager.save(product);
        }
    }

    private static void updateCategoryById() {
        List<Category> all = categoryManager.getAll();
        for (Category category : all) {
            System.out.println(category);
        }
        System.out.println("Please choose category id");
        int id = Integer.parseInt(scanner.nextLine());
        if (categoryManager.getById(id) != null) {
            System.out.println("Please input category name");
            String categoryStr = scanner.nextLine();
            String[] categoryData = categoryStr.split(",");
            Category category = new Category();
            category.setId(id);
            category.setName(categoryData[0]);
            categoryManager.update(category);
            System.out.println("Category was updated!");
        } else {
            System.out.println("Category does not exists");
        }
    }

    private static void updateProductById() {
        List<Product> productList = productManager.getAll();
        productList.forEach(System.out::println);
        System.out.println("Please choose product id");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            if (productManager.getById(id) != null) {

                boolean isRun = true;
                while (isRun) {
                    System.out.println("Updating product " + id);
                    System.out.println("         Please input 0 for exit");
                    System.out.println("         Please input 1 to update name");
                    System.out.println("         Please input 2 to update description");
                    System.out.println("         Please input 3 to update price");
                    System.out.println("         Please input 4 to update quantity");
                    System.out.println("         Please input 5 to update Category");
                    String command = scanner.nextLine();
                    switch (command) {
                        case "0":
                            isRun = false;
                            break;
                        case "1":
                            updateProductNameById(id);
                            break;
                        case "2":
                            updateProductDescriptionById(id);
                            break;
                        case "3":
                            updateProductPriceById(id);
                            break;
                        case "4":
                            updateProductQuantityById(id);
                            break;
                        case "5":
                            updateProductCategoryById(id);
                            break;
                        default:
                            System.out.println("Invalid Command");
                    }
                }




//                System.out.println("Please input product name,description,price,quantity");
//                String productStr = scanner.nextLine();
//                String[] productData = productStr.split(",");
//                Product product = new Product();
//                product.setId(id);
//                product.setName(productData[0]);
//                product.setDescription(productData[1]);
//                product.setPrice(Integer.parseInt(productData[2]));
//                product.setQuantity(Double.parseDouble(productData[3]));
//                productManager.update(product);
//                System.out.println("Product was updated!");
            } else {
                System.out.println("Product does not exists");
            }
        }catch (NumberFormatException e){
            System.err.println("ID must be integer");
        }
    }

    private static void updateProductNameById(int id) {
        System.out.println("Please input product new name");
        String pName = scanner.nextLine();
        Product product = new Product();
        product.setId(id);
        product.setName(pName);
        productManager.updateName(product);
        System.out.println("Product name was updated!");
    }

    private static void updateProductDescriptionById(int id) {
        System.out.println("Please input product new description");
        String description = scanner.nextLine();
        Product product = new Product();
        product.setId(id);
        product.setDescription(description);
        productManager.updateDescription(product);
        System.out.println("Product description was updated!");
    }

    private static void updateProductPriceById(int id) {
        System.out.println("Please input product new price");
        String price = scanner.nextLine();
        Product product = new Product();
        product.setId(id);
        product.setPrice(Integer.parseInt(price));
        productManager.updatePrice(product);
        System.out.println("Product description was updated!");
    }

    private static void updateProductQuantityById(int id) {
        System.out.println("Please input product new quantity");
        String quantity = scanner.nextLine();
        Product product = new Product();
        product.setId(id);
        product.setQuantity(Double.parseDouble(quantity));
        productManager.updateQuantity(product);
        System.out.println("Product description was updated!");
    }

    private static void updateProductCategoryById(int id) {
        List<Category> all = categoryManager.getAll();
        for (Category category : all) {
            System.out.println(category);
        }
        System.out.println("Please choose category id");
        int categoryId = Integer.parseInt(scanner.nextLine());
        Category category = categoryManager.getById(categoryId);
        if (category != null) {
            Product product = new Product();
            product.setId(id);
            product.setCategory(category);
            productManager.updateCategory(product);
            System.out.println("Product category was updated!");
        }else{
            System.out.println("Wrong category id");
        }
    }
}
