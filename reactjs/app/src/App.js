import React from 'react';
import axios from 'axios';
import { Chart } from 'primereact/chart';
import { Dropdown } from 'primereact/dropdown';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';

class App extends React.Component {
  constructor(entity){
    super(entity);
    this.state = {
      covidDatas : [],
      id:0,
      textString:'',
      selectedCity: "Tüm Şehirler"
    };


  this.options = this.getLightTheme();
  this.onCityChange = this.onCityChange.bind(this);
  }
  onCityChange(e) {
    console.log("city",e)

    this.setState({ selectedCity: e.value.name,
     
    });
    this.getGraphData(e.value.name);
    }
  getLightTheme() {
    let basicOptions = {
        legend: {
            labels: {
                fontColor: '#495057'
            }
        },
        scales: {
            xAxes: [{
                ticks: {

                    fontColor: '#495057'
                }
            }],
            yAxes: [{
                ticks: {
                    beginAtZero: true,
                    fontColor: '#495057'
                }
            }]
        }
    };
    return {
      basicOptions
  }
  }
  componentDidMount(){

    axios.get("http://localhost:8080/api/").then((res) =>{
      this.setState({
        covidDatas:res.data,
        id:0,
        textString:'',
        data: {
          labels: [],
          datasets: [
              {
                  label: 'vaka',
                  backgroundColor: '#42A5F5',
                  data: []
              },
              {
                  label: 'vefat',
                  backgroundColor: '#FFA726',
                  data: []
              },
              {
                label: 'taburcu',
                backgroundColor: '#FFF726',
                data: []
            }
          ]
      }
      })
    this.getGraphData(this.state.selectedCity);
    })
    
  
  }
  submit(event,id){
    event.preventDefault();
    if(id===0){
      axios.post("http://localhost:8080/api/",{
        textString:this.state.textString
      }).then(()=>{
        this.componentDidMount();
      })
    }else{
      axios.put("http://localhost:8080/api/",{
        id:id,
        textString:this.state.textString
      }).then(()=>{
        this.componentDidMount();
      })
    }
  }
  delete(id){
    axios.delete("http://localhost:8080/api/"+id)
    .then(()=>{
      this.componentDidMount();
    })
  }
  edit(id){
    axios.get("http://localhost:8080/api/"+id).then((res) => {
      this.setState({
      id:res.data.id,
      textString:res.data.textString
    
    });
    })
  }
 
  getGraphData(cityName){
    var _data = {
      labels: [],
      datasets: [
          {
              label: 'vaka',
              backgroundColor: '#42A5F5',
              data: []
          },
          {
              label: 'vefat',
              backgroundColor: '#FFA726',
              data: []
          },
          {
            label: 'taburcu',
            backgroundColor: '#FFF726',
            data: []
        }
      ]
  };
    axios.get("http://localhost:8080/graphData/1").then((response) => {
      let _cityNames =[]
      console.log("asd",response.data)
      Object.entries(response.data).forEach(element => {
        _cityNames.push({
          name: element[0]
        });
      });
      Object.entries(response.data[cityName]).forEach(element => {
        _data.labels.push(element[0]);
        _data.datasets.find(a => a.label === "vefat").data.push(element[1].vefat)
        _data.datasets.find(a => a.label === "taburcu").data.push(element[1].taburcu)
        _data.datasets.find(a => a.label === "vaka").data.push(element[1].vaka)
      });
      
      this.setState({
        data: _data,
        cityNames: _cityNames
      })
    }, (error) => {
      console.log(error);
    });
  }
  
  render(){

    const { basicOptions } = this.options;
    return(
    <div className="container">
    <div className="row">
    <div className="col s6">
            <form onSubmit={(e)=>this.submit(e,this.state.id)}>
            <div className="input-field col s12">
               <i className="material-icons prefix">person</i>
               <input value={this.state.textString} onChange={(e)=>this.setState({textString:e.target.value})} type="text"    />
               <label htmlFor="autocomplete-input"></label>
             </div>
             <button className="btn waves-effect waves-light right" type="submit" name="action">Haberi Yükle
               <i className="material-icons right">send</i>
             </button>
            </form>
     </div>          
     
                <div className="row">
                <div className="col s6">
            
                <div className="card">
                
                    <h5>Covid19 Tablosu</h5>
                    <Dropdown value={this.state.selectedCity} options={this.state.cityNames} onChange={this.onCityChange} optionLabel="name" placeholder={this.state.selectedCity} />
                    <Chart  type="bar" data={this.state.data} options={basicOptions} />
                </div>
                
          
                </div>
                <div className="col s6">
                  
                </div>
     <table>
   <thead>
     <tr>
         <th>Haberler</th>
     </tr>
   </thead>
   <tbody>
       {
         this.state.covidDatas.map(covid =>
             <tr key={covid.id}>
                 <td>{covid.textString}</td>
                 <td>
                   <button onClick={(e)=>this.edit(covid.id)} className="btn waves-effect waves-light" type="submit" name="action">
                     <i className="material-icons ">edit</i>
                   </button>       
                 </td>
                 <td>
                   <button onClick={(e)=>this.delete(covid.id)} className="btn waves-effect waves-light " type="submit" name="action">
                     <i className="material-icons ">delete</i>
                   </button>       
                 </td>
             </tr>
           )
       }
   </tbody>
 </table>
     </div>                
     </div>              
 </div>
);
  }
}
export default App;
