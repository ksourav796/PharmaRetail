app.directive("addCustomerModal", function ($http, Notification, $timeout) {
    return {
        restrict: 'E',
        templateUrl: "partials/addCustomerModal.html",
        link: function ($scope, element, attrs) {
            $scope.addCustomer = function () {
                $scope.customerNameText = "";
                $scope.GSTINText = "";
                $scope.customerEmailText = "";
                $scope.customerContactText = "";
                $scope.customerAddressText = "";
                $scope.pincode = "";
                $scope.personInchargeText = "";
                $scope.custStatusText = "Active";
                $scope.bankNameText = "";
                $scope.accNoText = "";
                $scope.panNumberText = "";
                $scope.bankBranchText = "";
                $scope.IFSCText = "";
                $scope.websiteText = "";
                $scope.creditTermText = "";
                $scope.creditLimitText = "";
                $('#modal-title').text("Add Customer");
                $("#submit").text("Save");
                $(".loader").css("display", "block");
                var requestUrl = '/pos' + '/getAllLists';
                $http.post(requestUrl).then(function (response) {
                    var data = response.data;
                    $scope.stateDTOList = angular.copy(data.stateDTOList);
                    $scope.countryDTOList = data.countryDTOList;
                    $scope.currencyDTOList = data.currencyDTOList;
                    $scope.custStatusText = "Active";
                    $("#addCustomer").modal('show');
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                })
            };

            $scope.saveCustomer = function () {
                var cust = true;
                var indiaRegex = /^[0-9]{2}[a-zA-Z]{5}[0-9]{4}[a-zA-Z0-9]{2}(z|Z)[a-zA-Z0-9]{1}$/;
                var ObjVal = $scope.GSTINText;
                var mail = $scope.customerEmailText;
                var mailRegex = /([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)\S+/;
                if (angular.isUndefined($scope.customerNameText) || $scope.customerNameText == '') {
                    Notification.warning({message: 'Customer Name can not be empty', positionX: 'center', delay: 2000});
                    cust = false;
                }
                if ($scope.GSTINText !== "" && $scope.selectedCountry == 1) {
                    if (angular.isUndefined($scope.GSTINText) || $scope.GSTINText !== $scope.GSTINText) {
                        Notification.warning({message: 'Please Enter Valid GST No', positionX: 'center', delay: 2000});
                        cust = false;
                    }
                    if ($scope.selectedCountry == 1) {
                        if (ObjVal.match(indiaRegex)) {
                            cust = true;
                        }
                        else {
                            Notification.error({message: 'Enter Valid GST Format', positionX: 'center', delay: 2000});
                            cust = true;
                        }
                    }
                }
                if (cust == true) {
                    $scope.saveCustomerOBJ();
                }


            };
            $scope.saveCustomerOBJ = function () {
                if (angular.isUndefined( $scope.selectedCountry) || $scope.selectedCountry === "") {
                    Notification.warning({message: 'Country can not be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.selectedState) ||$scope.selectedState === "") {
                    Notification.warning({message: 'State can not be empty', positionX: 'center', delay: 2000});
                }
                else if (angular.isUndefined($scope.selectedCurrency) || $scope.selectedCurrency === " ") {
                    Notification.warning({message: 'Currency can not be empty', positionX: 'center', delay: 2000});
                }
                else {
                    $scope.isDisabled = true;
                    $timeout(function () {
                        $scope.isDisabled = false;
                    }, 3000)
                    var saveCustomerDetails;
                    saveCustomerDetails = {
                        customerName: $scope.customerNameText,
                        customerId: $scope.customerId,
                        customerEmail: $scope.customerEmailText,
                        customerContact: $scope.customerContactText,
                        billingAddress: $scope.customerAddressText,
                        gstIn: $scope.GSTINText,
                        state: $scope.selectedState,
                        personIncharge: $scope.personInchargeText,
                        country: $scope.selectedCountry,
                        currency: $scope.selectedCurrency,
                        status: $scope.custStatusText,
                        bankName: $scope.bankNameText,
                        accountNo: $scope.accNoText,
                        branchName: $scope.bankBranchText,
                        iFSCCode: $scope.IFSCText,
                        uin: $scope.UINText,
                        website: $scope.websiteText,
                        panNumber: $scope.panNumberText,
                        creditTerm: $scope.creditTermText,
                        creditedLimit: $scope.creditLimitText
                    };
                    $http.post('/pos' + '/saveCustomer', angular.toJson(saveCustomerDetails, "Create"))
                        .then(function (response) {
                            var data = response.data;
                            if (data == "") {
                                Notification.error({
                                    message: 'NAME or GSTIN Already exists',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }
                            else {
                                $("#addCustomer").modal('hide');
                                if (!angular.isUndefined(data) && data !== null) {
                                    $("#cus_step2").removeClass("in active");
                                    $("#cus_step1").addClass("tab-pane fade in active");
                                }
                                $scope.getCustomerList();
                                Notification.success({
                                    message: 'Customer Created  successfully',
                                    positionX: 'center',
                                    delay: 2000
                                });
                            }
                            $scope.removeCustomerDetails();
                        }, function (error) {
                            Notification.error({
                                message: 'Something went wrong, please try again',
                                positionX: 'center',
                                delay: 2000
                            });
                        });
                }
                ;
            }
            $scope.next_wizard = function () {
                $("#addCustomer  #cus_step1").removeClass("in active");
                $("#addCustomer  #cus_step2").addClass("tab-pane fade in active");
            }

            $scope.back_wizard = function () {
                $("#addCustomer  #cus_step2").removeClass("in active");
                $("#addCustomer  #cus_step1").addClass("tab-pane fade in active");
            }
            $scope.countryState = function (country) {
                var url = "/pos/getCountryState?countryName=" + country;
                $http.post(url).then(function (response) {
                    var data = response.data;
                    $scope.stateDTOList = angular.copy(data);
                })
            }

            $scope.setCurrency = function () {
                $scope.selectedCurrency = $scope.selectedCountry;
            }
        }
    }
});