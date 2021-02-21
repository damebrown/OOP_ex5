package Orders;

import filesprocessing.DirectoryProcessor;

/**
 * singleton method, a factory of order subsections
 */
public class OrderFactory {

    /*class constants*/
    private static OrderFactory orderFactory = new OrderFactory();
    private static Order order = null;
    /*REVERSE String*/
    private static final String REVERSE = "#REVERSE";
    /*size String*/
    private static final String SIZE = "size";
    /*type String*/
    private static final String TYPE = "type";
    /*abs String*/
    private static final String ABS = "abs";

    private OrderFactory(){}

    /**
     * the singleton's getInstance function
     * @return the instance
     */
    public static OrderFactory getInstance(){
        return orderFactory;
    }

    /**
     * checks if the order arguments is valid
     * @param command the order argument command line
     * @return true if the line is valid, false else
     */
    public static boolean parseOrder(String command){
        String orderType = typeTest(command);
        if (command.endsWith(orderType+ REVERSE)){
            instanciateOrder(orderType, isReversed(command));
        } else if (command.endsWith(orderType)) {
            instanciateOrder(orderType, isReversed(command));
        } else {
            instanciateOrder(orderType, false);
        }
        return((command.endsWith(orderType+ REVERSE))||((command.endsWith(orderType))));
    }

    /**
     * this function gets the order details and makes order instances accordingly
     * @param orderType the order type
     * @param isReversed if reverse order
     */
    private static void instanciateOrder(String orderType, boolean isReversed){
        switch (orderType){
            case SIZE:
                order = new SizeOrder(isReversed, DirectoryProcessor.filteredFilesArray);
                break;
            case TYPE:
                order = new TypeOrder(isReversed, DirectoryProcessor.filteredFilesArray);
                break;
            default:
                order = new AbsoluteOrder(isReversed, DirectoryProcessor.filteredFilesArray);
                break;
        }
    }

    /**
     * this function checks which type of order is needed
     * @param command the command line
     * @return the type string
     */
    private static String typeTest(String command){
        if (command.startsWith(SIZE)){
            return SIZE;
        } else if (command.startsWith(TYPE)){
            return TYPE;
        } else {
            return ABS;
        }
    }

    /**
     * checks if the command includes reverse order
     * @param command the command line
     * @return true if reversed is asked
     */
    private static boolean isReversed(String command){
        return (command.endsWith(REVERSE));
    }

    /**
     * getter function for the order instance
     * @return the order instance
     */
    public static Order getOrder() {
        return order;
    }
}
