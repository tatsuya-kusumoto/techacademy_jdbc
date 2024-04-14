package jp.co.kiramex.dbSample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.kiramex.dbSample.entity.Country;
import jp.co.kiramex.dbSample.util.DatabaseManager;

public class CountryDAO {
    // データベース接続と結果取得のための変数
    private PreparedStatement pstmt;
    private ResultSet rs;

    /*
     * 検索結果に合致するCountryオブジェクトリストを取得するメソッド
     */
    public List<Country> getCountryFromName(String name) {
        // メソッドの結果として返すリスト
        List<Country> results = new ArrayList<Country>();

        try {
            // ドライバ読み込み
            Connection con = DatabaseManager.getConnection();

            // DBとのやりとりをする窓口（Statementオブジェクト）の作成
            String sql = "SELECT * FROM country WHERE Name = ?";
            pstmt = con.prepareStatement(sql);

            // select文の実行と結果を格納／代入
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            // 結果を表示
            while (rs.next()) {
                // 1件ずつCountryオブジェクトを作成した結果を詰める
                Country country = new Country();
                country.setName(rs.getString("name"));
                country.setPopulation(rs.getInt("population"));

                // リストに追加
                results.add(country);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                }catch(SQLException e) {
                    e.printStackTrace();
                }
            }
            if(pstmt != null) {
                try {
                   pstmt.close();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return results;
    }
}
