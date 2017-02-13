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
    public static final String JIAN_XIU = "jianxiu.pdf";
    public static final String SHU_DIAN = "shudian.pdf";
    public static final String AN_QUAN = "anquan.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_service_introduce_details_layout);
        int position = this.getIntent().getIntExtra("position", 0);
        String title = "电力运维";
        String path = AN_QUAN;
        switch (position) {
            case 0:
                title = "电力运维";
                path = AN_QUAN;
                break;
            case 1:
                title = "电力检修";
                path = JIAN_XIU;
                break;
            case 2:
                title = "电力抢修";
                path = AN_QUAN;
                break;
            case 3:
                title = "电气调试";
                path = AN_QUAN;
                break;
            case 4:
                title = "技术服务";
                path = AN_QUAN;
                break;
            case 5:
                title = "输电线路运检";
                path = SHU_DIAN;
                break;
            case 11:
                title = "安全文明";
                path = AN_QUAN;
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
