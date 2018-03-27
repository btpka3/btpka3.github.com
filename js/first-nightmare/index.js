const Nightmare = require('nightmare');
const nightmare = Nightmare({show: true});
const fs = require('fs');

nightmare
    .goto('https://music.163.com/#/discover/toplist')
    //.type('#search_form_input_homepage', 'github nightmare')
    //.click('[data-module]="discover"')
    // .wait(1000)
    // .wait('iframe[name="contentFrame"]')
    .evaluate(() => {

        // 查询 iframe , 查询表格
        var trList = document.querySelector('iframe[name="contentFrame"]')
            .contentDocument.querySelectorAll('table.m-table-rank tbody tr')
            .values();

        var rankArr = Array.from(trList)
            .map(tr => {
                return {
                    // 排行序号
                    "num": tr.querySelector("td:first-child  div.hd span.num").innerHTML,
                    // id
                    "id": tr.querySelector("td:nth-child(2) span[data-res-id]").getAttribute("data-res-id"),
                    // 标题
                    "title": tr.querySelector("td:nth-child(2) span.txt b").getAttribute("title"),
                    // 歌手
                    "title": tr.querySelector("td:last-child div.text[title]").getAttribute("title")

                    // 评论数- 待后续补充
                    //"commentCount"

                };
            });

        return rankArr;
    })
    .then(
        async rankArr => {
            for (var rank of rankArr) {
                await nightmare.goto('https://music.163.com/#/song?id=' + rank.id)
                    .evaluate(() => {
                        return document.querySelector('iframe[name="contentFrame"]')
                            .contentDocument.querySelector('#cnt_comment_count').innerHTML;
                    })
                    .then(
                        count => {
                            rank.commentCount = count;
                            console.log("=====" + rank.num + " : " + rank.commentCount);
                        },
                        err => {
                            console.log("-----" + err + " : " + rank.id)
                        });
            }
            //nightmare.halt(); FIXME not tested this line

            console.log(rankArr);
            var json = JSON.stringify(rankArr, null, 4);
            fs.writeFile('163.rank100.json', json, 'utf8', () => {
            });
        },
        err => {
            console.log("###### Oh, Error : " + err)
        });
