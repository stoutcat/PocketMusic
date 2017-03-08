package com.example.q.pocketmusic.model.net;

import android.os.AsyncTask;

import com.example.q.pocketmusic.config.Constant;
import com.example.q.pocketmusic.model.bean.Song;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by YQ on 2016/8/29.
 */
public class LoadTypeSongList extends AsyncTask<String, Void, Integer> {
    private int typeId;
    private List<Song> list;

    protected LoadTypeSongList(List<Song> lists, int typeId) {
        this.typeId = typeId;
        this.list = lists;
        list.clear();
    }

    /**
     * 通过类型列表的position得到list
     *
     * @param strings 完整的某种乐器的url
     * @return 里诶包
     */
    @Override
    protected Integer doInBackground(String... strings) {
        String typeUrl = strings[0];
        try {
            Document doc = Jsoup.connect(typeUrl).userAgent(Constant.USER_AGENT).timeout(6000).get();
            Element tbody =  doc.select("table.opern_list").get(0).getElementsByTag("tbody").get(0);
            Elements trs = tbody.getElementsByTag("tr");
            trs.remove(23);
            trs.remove(17);
            trs.remove(11);
            trs.remove(5);//一定要倒着去横线！！不然会顺序错误
            for (Element tr : trs) {
                Elements tds=tr.getElementsByTag("td");
                Element a=tds.get(1).getElementsByTag("a").get(0);
                String name=a.text();
                String url=Constant.BASE_URL+a.attr("href");
                String artist=tds.get(4).text();
                String date=tds.get(6).text();
                Song song=new Song(name,url);
                song.setArtist(artist);
                song.setDate(date);
                song.setTypeId(typeId);
                list.add(song);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.FAIL;
        }
        return Constant.SUCCESS;
    }


}
