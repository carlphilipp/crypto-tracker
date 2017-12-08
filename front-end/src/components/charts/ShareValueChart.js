import React, {Component} from 'react';
import {LineChart, XAxis, YAxis, Tooltip, CartesianGrid, Line, Legend} from 'recharts';


const data = [
      {name: 'Page A', pv: 2400, amt: 2400},
      {name: 'Page B',  pv: 1398, amt: 2210},
      {name: 'Page C',  pv: 9800, amt: 2290},
      {name: 'Page D',  pv: 3908, amt: 2000},
      {name: 'Page E',  pv: 4800, amt: 2181},
      {name: 'Page F',  pv: 3800, amt: 2500},
      {name: 'Page G', pv: 4300, amt: 2100},
      {name: 'Page A', pv: 2400, amt: 2400},
      {name: 'Page B',  pv: 1398, amt: 2210},
      {name: 'Page C',  pv: 9800, amt: 2290},
      {name: 'Page D',  pv: 3908, amt: 2000},
      {name: 'Page E',  pv: 4800, amt: 2181},
      {name: 'Page F',  pv: 3800, amt: 2500},
      {name: 'Page G', pv: 4300, amt: 2100},
      {name: 'Page A', pv: 2400, amt: 2400},
      {name: 'Page B',  pv: 1398, amt: 2210},
      {name: 'Page C',  pv: 9800, amt: 2290},
      {name: 'Page D',  pv: 3908, amt: 2000},
      {name: 'Page E',  pv: 4800, amt: 2181},
      {name: 'Page F',  pv: 3800, amt: 2500},
      {name: 'Page G', pv: 4300, amt: 2100},
      {name: 'Page A', pv: 2400, amt: 2400},
      {name: 'Page B',  pv: 1398, amt: 2210},
      {name: 'Page C',  pv: 9800, amt: 2290},
      {name: 'Page D',  pv: 3908, amt: 2000},
      {name: 'Page E',  pv: 4800, amt: 2181},
      {name: 'Page F',  pv: 3800, amt: 2500},
      {name: 'Page G', pv: 4300, amt: 2100},
      {name: 'Page A', pv: 2400, amt: 2400},
      {name: 'Page B',  pv: 1398, amt: 2210},
      {name: 'Page C',  pv: 9800, amt: 2290},
      {name: 'Page D',  pv: 3908, amt: 2000},
      {name: 'Page E',  pv: 4800, amt: 2181},
      {name: 'Page F',  pv: 3800, amt: 2500},
      {name: 'Page G', pv: 4300, amt: 2100},
];

class CustomizedLabel extends Component{
  render () {
    const {x, y, stroke, value} = this.props;

   	return <text x={x} y={y} dy={-4} fill={stroke} fontSize={10} textAnchor="middle">{value}</text>
  }
}

class CustomizedAxisTick extends Component{
  render () {
    const {x, y, stroke, payload} = this.props;

   	return (
    	<g transform={`translate(${x},${y})`}>
        <text x={0} y={0} dy={16} textAnchor="end" fill="#666" transform="rotate(-35)">{payload.value}</text>
      </g>
    );
  }
}

class SimpleLineChart extends Component {
	render () {
  	return (
    	<LineChart width={1100} height={300} data={data} margin={{top: 20, right: 30, left: 20, bottom: 10}}>
         <XAxis dataKey="name" height={60} tick={<CustomizedAxisTick/>}/>
         <YAxis/>
         <CartesianGrid strokeDasharray="3 3"/>
         <Tooltip/>
         <Legend />
         <Line type="monotone" dataKey="pv" stroke="#8884d8" />
      </LineChart>
    );
  }
}

export default SimpleLineChart;
