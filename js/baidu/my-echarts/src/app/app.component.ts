import {ChangeDetectorRef, Component, OnChanges, OnInit, SimpleChanges} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnChanges {

  now = new Date("2017/11/9");
  title = 'app';

  staffs: Object[][];
  staffTypes: Object[];
  ss: Object[][];

  echartsOption: any;


  constructor(private cdRef: ChangeDetectorRef) {

  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log('====', changes)
  }


  cc() {
    //this.cdRef.detectChanges();
    console.log("========cc()")
    this.echartsOption = this.getEchartsOption(this.getSelectedStaffs());
  }


  private initData(): Object[][] {


    let recs: Object[][] = [

      // 离职
      ["Web前端", "梅梦遥", "2015/5/25", "2015/7/14"],
      ["Java后台", "宋其忠", "2015/7/1", "2015/8/15"],
      ["UI", "邓文君", "2015/6/25", "2015/11/10"],
      ["Web前端", "叶晨浩", "2015/8/17", "2016/3/18"],
      ["Java后台", "胡杨", "2015/6/2", "2016/4/27"],
      ["Web前端", "菩提", "2015/5/20", "2016/5/6"],
      ["Java后台", "青蒿", "2015/8/17", "2016/5/7"],
      ["UI", "铃兰", "2016/5/24", "2016/10/7"],
      ["Web前端", "梦杨", "2016/6/6", "2016/10/7"],
      ["UI", "小卉", "2015/11/16", "2016/11/25"],
      ["Java后台", "唐印", "2015/6/30", "2017/2/28"],
      ["Java后台", "汀舟", "2016/5/23", "2017/3/31"],
      ["Web前端", "远洋", "2016/7/1", "2017/3/31"],
      ["Java后台", "炮灰", "2016/5/30", "2017/4/28"],
      ["产品", "风信", "2016/4/19", "2017/6/30"],
      ["Java后台", "王颜", "2017/3/20", "2017/6/30"],
      ["Java后台", "艾文", "2017/7/24", "2017/8/10"],
      ["Java后台", "东天", "2017/8/21", "2017/9/11"],
      ["UI", "言一", "2016/10/31", "2017/9/22"],
      ["测试", "平平", "2016/6/6", "2017/10/31"],

      // 在职
      ["Java后台", "般若", "2015/5/1"],
      ["产品", "可可", "2015/5/1"],
      ["测试", "毛豆豆", "2015/9/7"],
      ["产品", "萝卜", "2016/4/25"],
      ["Web前端", "沐风", "2016/5/30"],
      ["Java后台", "京九", "2017/3/20"],
      ["Web前端", "重阳", "2017/3/27"],
      ["Web前端", "仁毅", "2017/7/3"],
      ["Java后台", "青橙", "2017/7/24"],
      ["Java后台", "齐格", "2017/8/28"],
      ["UI", "琉璃", "2017/9/18"],
      ["Java后台", "安莫", "2017/10/10"],
      ["测试", "白菜", "2017/10/16"],
    ];


    recs.forEach((s: object[]) => {

      // 日期字符串 -> Date
      for (let i = 2; i < s.length; i++) {
        s[i] = new Date(<string><any>s[i])
      }

      // 没有离职的，追加离职时间为当前时间。
      if (s.length == 3) {
        s.push(this.now)
      }
    });

    // 按照入职时间排序
    recs.sort((a, b) => {
      return (<Date>b[2]).getTime() - (<Date>a[2]).getTime();
    });

    return recs;
  }

  private getSelectedStaffs(): Object[][] {
    let seletedTypes: string[] = this.staffTypes
      .filter((t) => t['checked'])
      .map((t) => t['name']);
    return this.staffs.filter((s) => seletedTypes.includes(<string>s[0]))
  }

  private getEchartsOption(staffs: Object[][]): any {

    let _this = this;

    return {
      title: {
        text: '员工在职时间表'
      },
      legend: {},
      xAxis: {
        type: 'time'
      },

      yAxis: {
        type: 'category',
        data: staffs.map((s) => s[1])
      },
      tooltip: {
        show: true,
        trigger: 'axis',
        formatter: function (params) {
          let res = params[0].name + "</br>";
          let date0 = params[0].data;
          let date1 = params[1].data;
          date0 = date0.getFullYear() + "-" + (date0.getMonth() + 1) + "-" + date0.getDate();
          if (date1 != _this.now) {
            date1 = date1.getFullYear() + "-" + (date1.getMonth() + 1) + "-" + date1.getDate();
          } else {
            date1 = "至今";
          }
          res += params[0].seriesName + ":" + date0 + "</br>";
          res += params[1].seriesName + ":" + date1 + "</br>";
          return res;
        }
      },
      grid: {
        left: '12%',
        right: '10%'
      },
      series: [
        {
          type: 'bar',
          name: '入职时间',
          stack: '总量',
          barWidth: '20%',
          itemStyle: {
            normal: {
              color: 'rgba(0,0,0,0)'
            }
          },
          data: staffs.map((s) => s[2])
        },
        {
          name: '离职时间',
          type: 'bar',
          stack: '总量',
          barWidth: '20%',
          itemStyle: {
            normal: {
              color: '#25B59E',
              barBorderRadius: 0,//柱形边框圆角半径，单位px，支持传入数组分别指定柱形4个圆角半径
              shadowColor: 'rgba(0, 0, 0, 0)',//阴影颜色。支持的格式同color
              shadowBlur: 0//图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
            }
          },
          data: staffs.map((s) => s.length >= 4 ? s[3] : null)
        }
      ]
    };

  }

  ngOnInit() {


    this.staffs = this.initData();

    // 统计出所有的员工类型
    this.staffTypes = Array.from(new Set(this.staffs.map((s) => <string>s[0]
    ))).map((s) => {
      return {
        name: <string>s,
        checked: true
      }
    });
    this.cc();

  }

}
