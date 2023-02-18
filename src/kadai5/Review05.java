package kadai5;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        Connection con = null;
        PreparedStatement spstmt = null;    // 更新前、更新後の検索用プリペアードステートメントオブジェクト
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String sql ="select * from person where id = ?";


            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "Kosuke0531");

            spstmt = con.prepareStatement(sql);

            System.out.println("検索するidを入力してください！");
            int num1 = Integer.parseInt(keyIn());
            spstmt.setInt(1, num1);
            rs = spstmt.executeQuery();

            {
             while(rs.next()) {
                    String name = rs.getString("Name");
                    int age = rs.getInt("Age");

                    System.out.println(name+"\t"+age);
                }
            }
        }catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました");
            e.printStackTrace();
        }catch (SQLException e) {
            System.err.println("データベースに異常が発生しました");
            e.printStackTrace();
        }finally {
            if(rs != null) {
                try {
                    rs.close();
                }catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }if(spstmt != null) {
                try {
                    spstmt.close();
                }catch (SQLException e) {
                    System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                } if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException e) {
                        System.err.println("データベース切断時にエラーが発生しました。");
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {

        }
        return line;
    }

}


