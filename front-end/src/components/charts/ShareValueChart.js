import React, {Component} from 'react';
import {LineChart, XAxis, YAxis, Tooltip, CartesianGrid, Line, Legend} from 'recharts';

class CustomizedAxisTick extends Component{
  render () {
    const {x, y, payload} = this.props;

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
    	<LineChart width={1100} height={300} data={this.props.shareValues} margin={{top: 20, right: 30, left: 20, bottom: 10}}>
         <XAxis dataKey="date" height={60} tick={<CustomizedAxisTick/>}/>
         <YAxis yAxisId="left" />
         <YAxis yAxisId="right" orientation="right" />
         <CartesianGrid strokeDasharray="3 3"/>
         <Tooltip/>
         <Legend />
         <Line yAxisId="left" type="monotone" dataKey="shareValue" stroke="#8884d8" activeDot={{r: 8}}/>
         <Line yAxisId="right" type="monotone" dataKey="portfolioValue" stroke="#82ca9d" />

         {/*}<Line type="monotone" dataKey="shareValue" stroke="#8884d8" /> */}
      </LineChart>
    );
  }
}

export default SimpleLineChart;
