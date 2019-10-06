package com.gojek.solution;

import com.gojek.processor.AbstractProcessor;
import com.gojek.processor.FileProcessor;
import com.gojek.processor.InteractiveParkingLotProcessor;

public class Main {
    public static void main(String[] args) throws Exception {
        AbstractProcessor processor = null;

        if(args.length >= 1) {
            processor = new FileProcessor(args[0]);
        } else {
            processor = new InteractiveParkingLotProcessor();
        }
        processor.printUsage();
        processor.process();
    }
}
