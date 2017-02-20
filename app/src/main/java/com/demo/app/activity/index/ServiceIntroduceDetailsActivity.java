package com.demo.app.activity.index;

import android.os.Bundle;

import com.demo.app.R;
import com.demo.app.activity.BaseActivity;
import com.demo.app.util.TitleCommon;
import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;


public class ServiceIntroduceDetailsActivity extends BaseActivity implements OnPageChangeListener {
    PDFView pdfView;
    Integer pageNumber = 1;
    public static final String JI_SHU = "jishuzixun.pdf";
    public static final String BIAN_DIAN = "biandianyuwei.pdf";
    public static final String JIAN_XIU = "dianlijianxiu.pdf";
    public static final String FENG_DIAN = "fengdianyunwei.pdf";
    public static final String JI_DIAN = "jidianyunwei.pdf";
    public static final String SHU_DIAN = "shudianxianlu.pdf";
    public static final String HUO_DIAN = "huodianyunwei.pdf";
    public static final String SHUI_DIAN = "shuidianyunwei.pdf";
    public static final String GUANG_FU = "guangfudianzhan.pdf";
    public static final String GUI_DAO = "guidaojiaotong.pdf";
    public static final String LIANG_PIAO = "liangpiaoguanli.pdf";
    public static final String AN_QUAN = "anquanwenming.pdf";
    public static final String FA_LV = "falvfagui.pdf";
    public static final String SHI_GU = "shiguanli.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_service_introduce_details_layout);
        int position = this.getIntent().getIntExtra("position", 0);
        String title = "变电运维";
        String path = BIAN_DIAN;
        switch (position) {
            case 0:
                title = "变电运维";
                path = BIAN_DIAN;
                break;
            case 1:
                title = "火电运维";
                path = HUO_DIAN;
                break;
            case 2:
                title = "水电运维";
                path = SHUI_DIAN;
                break;
            case 3:
                title = "风电运维";
                path = FENG_DIAN;
                break;
            case 4:
                title = "光伏电站";
                path = GUANG_FU;
                break;
            case 5:
                title = "机电运维";
                path = JI_DIAN;
                break;
            case 6:
                title = "轨道交通";
                path = GUI_DAO;
                break;
            case 7:
                title = "输电线路";
                path = SHU_DIAN;
                break;
            case 8:
                title = "电力检修";
                path = JIAN_XIU;
                break;
            case 9:
                title = "两票管理";
                path = LIANG_PIAO;
                break;
            case 10:
                title = "安全文明";
                path = AN_QUAN;
                break;
            case 11:
                title = "技术咨询";
                path = JI_SHU;
                break;
            case 12:
                title = "法律法规";
                path = FA_LV;
                break;
            case 13:
                title = "事故案例";
                path = SHI_GU;
                break;
            default:
                break;
        }
        TitleCommon.setTitle(this, null, title, ServiceIntroduceActivity.class, true);
        pdfView = (PDFView) this.findViewById(R.id.pdfview);
        display(path, true);
    }

    private void display(String assetFileName, boolean jumpToFirstPage) {
        if (jumpToFirstPage) {
            pageNumber = 1;
            pdfView.fromAsset(assetFileName)
                    .defaultPage(pageNumber)
                    .onPageChange(this)
                    .showMinimap(false)
                    .enableSwipe(true)
                    .load();
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
    }
}
