import processor.AbstractProcessor;
import processor.FileProcessor;
import processor.InteractiveParkingLotProcessor;

public class Main {
    public static void main(String[] args) throws Exception {
        AbstractProcessor processor = null;

        if(args.length >= 1) {
            processor = new FileProcessor(args[0]);
        } else {
            processor = new InteractiveParkingLotProcessor();
        }
        processor.process();
    }
}
