package source;
class Order extends ProductItem {
	
	
    private int orderId;

    public Order(String productName, int quantity, double price) {
        super(productName, quantity, price);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
  
}
