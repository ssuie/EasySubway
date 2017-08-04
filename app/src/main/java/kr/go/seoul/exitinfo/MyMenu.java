package kr.go.seoul.exitinfo;


import java.util.ArrayList;
import java.util.HashMap;

public class MyMenu {
    private ArrayList<String> subwayName;
    private HashMap<String, ArrayList<String>> mMenu;

    public MyMenu() {
        mMenu                   = new HashMap<>();
        subwayName = new ArrayList<>();

        setHashMap();
        setSubwayName();
    }

    private void setSubwayName(){
        subwayName.add("test");
        subwayName.add("tset");
        subwayName.add("테스트");
        subwayName.add("평촌");
        subwayName.add("1");
    }


    private void setHashMap() {
        mMenu.put("subway", subwayName);
    }

    // 각 메뉴 반환
    public ArrayList<String> getMenu(String menuTitle) { return mMenu.get(menuTitle); }
}
