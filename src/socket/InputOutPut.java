package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class InputOutPut {

    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private OutputStream outputStream ;
    private PrintWriter printWriter ;

    public InputOutPut(Socket socket) throws IOException {
        inputStreamReader = new InputStreamReader(socket.getInputStream());
        bufferedReader = new BufferedReader(inputStreamReader);
        outputStream = socket.getOutputStream();
        printWriter = new PrintWriter(outputStream,true);
    }

    public InputStreamReader getInputStreamReader() {
        return inputStreamReader;
    }
    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

}
