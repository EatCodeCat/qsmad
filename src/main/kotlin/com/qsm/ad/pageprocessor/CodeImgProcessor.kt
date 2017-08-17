package com.qsm.ad.pageprocessor

import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.Spider
import us.codecraft.webmagic.pipeline.Pipeline
import us.codecraft.webmagic.processor.PageProcessor

/**
 * Created by TQ on 2017/8/17.
 */
class CodeImgProcessor : PageProcessor {

    private val site = Site.me().setRetryTimes(3).setSleepTime(100)
    init {
        site.addHeader("host", "qsm.qoo10.jp")
        site.addHeader("connection", "keep-alive")
        site.addHeader("cache-control", "max-age=0")
        site.addHeader("pgrade-insecure-requests", "1")
        site.addHeader("content-type", "application/json")
        site.addHeader("user-agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
        site.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
        site.addHeader("accept-encoding", "gzip, deflate, sdch")
        site.addHeader("accept-language", "zh-CN,zh;q=0.8,en;q=0.6,ja;q=0.4,nb;q=0.2,sk;q=0.2,zh-TW;q=0.2")
        site.addHeader("cookie", "")
        site.setDomain("qsm.qoo10.jp")
    }

    override fun getSite(): Site {
        return site
    }

    override fun process(page: Page?) {
        var bytes = page?.bytes
        page?.putField("bytes", bytes)
    }
}

var url = "https://qsm.qoo10.jp/GMKT.INC.Gsm.Web/Common/Page/qcaptcha.ashx?qcaptchr_req_no=rCim42bFfU_g_2_5sHf9d2GyfjZ8fsHRfQyFYqnMMSQvjwFyU0HosCyLnHvIeqe4a4de"
fun getQsmLoginCodeResult(pip: Pipeline) {
    Spider.create(CodeImgProcessor()).addUrl(url).addPipeline(pip).thread(1).run()
}