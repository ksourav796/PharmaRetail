/**
 * Created by azgar.h on 7/1/2017.
 */

app.controller('supplierCtrl',
    function ($scope, $http, $location, $filter, Notification, $timeout, $window, $cookies,$rootScope) {
        $("#contact").addClass('active');
        $('#contact').siblings().removeClass('active');
        $("#supplier").addClass('active');
        $('#supplier').siblings().removeClass('active');

        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.regEx="/^[0-9]{10,10}$/";
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;
        $scope.inactiveStatus="Active";
        $scope.editSupplierPopulate = function (dataOBJ) {
            $http.post('/pos'+ '/editSupplier?supplierName=' + dataOBJ.supplierName).then(function (response) {
                var data = response.data;
                $scope.supplierId=data.supplierId;
                $scope.stateDTOList =data.stateDTOList;
                $scope.countryDTOList = data.countryDTOList;
                $scope.currencyDTOList = data.currencyDTOList;
                $scope.supplierNameText=data.supplierName;
                $scope.GSTINText=data.gstIn;
                $scope.selectedState=data.state;
                $scope.supplierContactText=data.supplierNumber;
                $scope.supplierEmailText=data.supplierEmail;
                $scope.supplierAddressText=data.billingAddress;
                $scope.personInchargeText=data.personIncharge;
                $scope.selectedCountry=data.country;
                $scope.selectedCurrency=data.currency;
                $scope.bankNameText=data.bankName;
                $scope.accNoText=data.accountNo;
                $scope.panNumberText=data.panNO;
                $scope.bankBranchText=data.branchName;
                $scope.IFSCText=data.iFSCCode;
                $scope.websiteText=data.website;
                $scope.creditTermText=data.creditDesc;
                $scope.creditLimitText=data.creditLimit;
                $scope.supStatusText=data.status;
                $("#addSupplier").modal('show');
                $('#modal-title').text("Edit Supplier");
                $("#submit").text("Update");
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.getSupplierList = function (page) {
            switch (page) {
                case 'firstPage':
                    $scope.firstPage = true;
                    $scope.lastPage = false;
                    $scope.isNext = false;
                    $scope.isPrev = false;
                    $scope.pageNo = 0;
                    break;
                case 'lastPage':
                    $scope.lastPage = true;
                    $scope.firstPage = false;
                    $scope.isNext = false;
                    $scope.isPrev = false;
                    $scope.pageNo = 0;
                    break;
                case 'nextPage':
                    $scope.isNext = true;
                    $scope.isPrev = false;
                    $scope.lastPage = false;
                    $scope.firstPage = false;
                    $scope.pageNo = $scope.pageNo + 1;
                    break;
                case 'prevPage':
                    $scope.isPrev = true;
                    $scope.lastPage = false;
                    $scope.firstPage = false;
                    $scope.isNext = false;
                    $scope.pageNo = $scope.pageNo - 1;
                    break;
                default:
                    $scope.firstPage = true;
            }
            var paginationDetails;
            paginationDetails = {
                firstPage: $scope.firstPage,
                lastPage: $scope.lastPage,
                pageNo: $scope.pageNo,
                prevPage: $scope.prevPage,
                prevPage: $scope.isPrev,
                nextPage: $scope.isNext
            }
            if (angular.isUndefined($scope.supplierSearchText)) {
                $scope.supplierSearchText = "";
            }
            $http.post('/pos' + '/getPaginatedSupplierList?type='+ $scope.inactiveStatus+ '&searchText=' + $scope.supplierSearchText, angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                $scope.supplierList = data.list;
                $scope.first = data.firstPage;
                $scope.last = data.lastPage;
                $scope.prev = data.prevPage;
                $scope.next = data.nextPage;
                $scope.pageNo = data.pageNo;
                var i=0;
                $scope.listStatus=true;
                // angular.forEach($scope.supplierList,function (value,key) {
                //     if(value.supplierName.toUpperCase()==type.toUpperCase()){
                //         if(value.status=='InActive')  {
                //             i++;
                //         }
                //     }
                // })
                // if(i>0){
                //     Notification.error({
                //         message: 'Supplier Name Became InActive',
                //         positionX: 'center',
                //         delay: 2000
                //     });
                // }

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };
        $scope.clicked = false;
        $scope.ButtonStatus = "InActive";
        $scope.inactiveSupplier = function(val) {
            if($scope.clicked == false){
                $scope.inactiveStatus = "InActive";
                $scope.ButtonStatus = "Active";
                var page="Page";
            }
            else{
                $scope.inactiveStatus = "Active";
                $scope.ButtonStatus = "InActive";
                var page="";
            }
            $scope.clicked = !$scope.clicked;

            $scope.getSupplierList();
        };

        $scope.getSupplierList();
        $scope.removeSupplierDetails = function () {
            $scope.supplierId="";
            $scope.supplierNameText = "";
            $scope.supplierEmailText = "";
            $scope.supplierContactText = "";
            $scope.supplierContactText = "";
            $scope.supplierAddressText = "";
            $scope.creditTermText = "" ;
            $scope.creditLimitText = "";
            $scope.companyRegNo="";
            $scope.fromRegNo="";
        };
        $scope.next_wizardEdit = function(){
            $("#sub_step12").removeClass("in active");
            $("#sub_step22").addClass("tab-pane fade in active");
        }
        $scope.back_wizardEdit = function(){
            $("#sub_step22").removeClass("in active");
            $("#sub_step12").addClass("tab-pane fade in active");
        }
        $scope.next_wizard = function(){
            $("#sub_step1").removeClass("in active");
            $("#sub_step2").addClass("tab-pane fade in active");
        }
        $scope.back_wizard = function(){
            $("#sub_step2").removeClass("in active");
            $("#sub_step1").addClass("tab-pane fade in active");
        }
        $scope.ValidatePAN= function(Obj) {
            if (Obj != "") {
                ObjVal = Obj;
                var panPat = /^([A-Z]{5})(\d{4})([A-Z]{1})$/;
                if (ObjVal.search(panPat) == -1) {
                    Notification.error({message: 'Enter Valid Pan No Format',positionX: 'center',delay: 2000});
                    Obj.focus();
                    return false;
                }
            }
        }

    });
