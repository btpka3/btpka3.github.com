module.exports = function (app, wx, pages, idx) {



    // 要跳转的页面，这里仅仅是示例。
    this.pages = pages;
    this.idx = idx;

    // 去往要跳转的页面
    this.go = function (idx) {
        if (this.pages
            && (0 <= idx && idx <= this.pages.length)
            && this.idx != idx) {
            wx.redirectTo(this.pages[idx])
        }
        console.log("---- ks-footer: go " + idx)
    }
};