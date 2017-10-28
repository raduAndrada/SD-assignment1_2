function CtListVm(data) {
	var self = this;

	self.cities = ko.observableArray([]);
	
	self.newCtNm = ko.observable();
	self.aplnTp = ko.observable();
	self.dprtCt = ko.observable();
	self.arrTm = ko.observable(moment(data.arrTm));
	self.nbSt = ko.observable();
	
	self.selectedCities = ko.observableArray([]); 
	

	self.addCity = function() {
		if ((self.newCtNm() != "") && (self.cities.indexOf(this.newCtNm()) < 0)){
		self.cities.push(this.newCtNm());
		}
		self.newCtNm("");
	};
	self.removeSelected = function() {
	
		self.cities.removeAll(self.selectedCities());
        self.selectedCities([]); // Clear selection
		//self.arrivalTimes.remove(this);
	}


	// console.log(self.cities());
	// var cts = self.cities();

	// call to get users list when the VM is loading or you can call it on any
	// event on your model
	self.updateCtList = function() {
		console.log(self.arrTm());
		new update(self.cities(), self.aplnTp(), self.dprtCt(), self.arrTm(), self.nbSt());
	}
}

function Ct(data) {
	this.name = ko.observable(data.name);
	this.latitude = ko.observable(data.latitude);
	this.longitude = ko.observable(data.longitude);
	this.arrivalTime = ko.observable(data.arrivalTime);

}

function update(cities, aplnTp, dprtCt, arrTm, nbSt) {
	$.ajax({
		type : "POST",
		dataType : "json",
		url : 'http://localhost:8080/assignment1_2/flights-users',
		data : {"citiesNames":cities.join(),"aplnTp":aplnTp,"dprtCt":dprtCt,"arrTm":arrTm.toString(),"nbSt":nbSt},
		success : function(response) {
				alert("The new flight has been added succesfully");
				console.log(response.redirect);
				window.location.href = "http://localhost:8080/assignment1_2/flights-users"
		}
	});
}
ko.bindingHandlers.datepicker = {
        init: function(element, valueAccessor, allBindingsAccessor) {
            //initialize datepicker with some optional options
            var options = allBindingsAccessor().datepickerOptions || {format: 'DD/MM/YYYY HH:mm'};
            $(element).datetimepicker(options);

            //when a user changes the date, update the view model
            ko.utils.registerEventHandler(element, "dp.change", function(event) {
                var value = valueAccessor();
                if (ko.isObservable(value)) {
                    value(event.date);
                }
            });
        },
        update: function(element, valueAccessor)   {
            var widget = $(element).data("DateTimePicker");
            //when the view model is updated, update the widget
            if (widget) {
                var date = ko.utils.unwrapObservable(valueAccessor());
                widget.date(date);
            }
        }
    };
var viewModel = {};
var defaultModel = {
	    arrTm: "/Date(1427368178393)/"
	}
viewModel = new CtListVm(defaultModel);

ko.applyBindings(viewModel);