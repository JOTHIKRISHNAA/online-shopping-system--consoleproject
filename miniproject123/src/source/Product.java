package source;

class Product {
    private int productId;
    private String productName;
    private double productPrice;
    private int productAvailability;

    public Product(String productName, double productPrice, int productAvailability) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productAvailability = productAvailability;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getProductAvailability() {
        return productAvailability;
    }

	public void setproductPrice(double updatedPrice) {
		// TODO Auto-generated method stub
		
	}

	public void setproductAvailability(int updatedAvailability) {
		// TODO Auto-generated method stub
		
	}

	public void setproductName(String updatedName) {
		// TODO Auto-generated method stub
		
	}
}
