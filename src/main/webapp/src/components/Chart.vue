<template>
  <div :id="chartId" class="chart-container"></div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  props: {
    chartId: {
      type: String,
      required: true,
    },
    chartName: {
      type: String,
      required: true,
    },
    url:{
      type: String,
      required: true
    }
  },
  mounted() {
    this.renderChart();
  },
  methods: {
    async renderChart() {
      const chartDom = document.getElementById(this.chartId);
      if (!chartDom) {
        console.error(`Chart element with ID '${this.chartId}' not found.`);
        return;
      }

      const myChart = echarts.init(chartDom);
      try {
        const response = await fetch(this.url);
        const data = await response.json();
        let xAxisData = [];
        let seriesData = {};

        data.body.forEach(item => {
          let time = new Date(item.time).toISOString().substring(11, 16);
          let type = item._id;

          if (!seriesData[type]) {
            seriesData[type] = Array(xAxisData.length).fill(0);
          }

          let index = xAxisData.indexOf(time);
          if (index === -1) {
            xAxisData.push(time);
            index = xAxisData.length - 1;
          }

          seriesData[type][index] = item.count;
        });

        let option = {
          title: { text: this.chartName },
          tooltip: { trigger: 'axis' },
          xAxis: { type: 'category', data: xAxisData },
          yAxis: { type: 'value' },
          series: Object.keys(seriesData).map(type => ({
            name: type,
            type: 'line',
            data: seriesData[type],
          }))
        };

        myChart.setOption(option);
      } catch (error) {
        console.error('Error fetching or rendering chart:', error);
      }
    }
  }
};
</script>

<style scoped>
.chart-container {
  width: 100%;
  height: 200px;
}
</style>