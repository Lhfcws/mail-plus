package server.test.email;

import edu.sysu.lhfcws.mailplus.commons.base.Consts;
import edu.sysu.lhfcws.mailplus.commons.io.CommonSocket;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author lhfcws
 * @time 14-10-26.
 */
public class FakeInteractiveSMTPShell {
    private CommonSocket socket;

    public static void main(String[] args) throws IOException {
        new FakeInteractiveSMTPShell().start();
    }

    public FakeInteractiveSMTPShell() throws IOException {
        this.socket = new CommonSocket();
        this.socket.connect("smtp.163.com", 25);
        System.out.print(this.socket.receive());
    }

    public void start() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String line;
        boolean block = true;

        while (true) {
            System.out.print("> ");
            line = scanner.nextLine();
            this.socket.send(line + Consts.CRLF);

            if (line.startsWith("Subject")) {
                block = false;
            }

            if (line.trim().equals(".")) {

                block = true;
            }

            if (block)
                System.out.print(this.socket.receive());

        }
    }
}
