/**
 * Created by azgar.h on 7/1/2017.
 */

app.controller('customerCtrl',
    function ($scope, $http, $location, $filter, Notification, ngTableParams, $timeout) {
        $("#contact").addClass('active');
        $('#contact').siblings().removeClass('active');
        $("#customer").addClass('active');
        $('#customer').siblings().removeClass('active');

        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.regEx="/^[0-9]{10,10}$/";
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;
        $scope.inactiveStatus="Active";

        $scope.clicked = false;
        $scope.ButtonStatus = "InActive";
        $scope.inactiveCustomer = function(val) {
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
            $scope.getCustomerList();
        };

        $scope.getCustomerList = function (type,page) {
            $(".loader").css("display", "block");

            if (angular.isUndefined(type)) {
                type = "";
            }
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
            if (angular.isUndefined($scope.customerSearchText)) {
                $scope.customerSearchText = "";
            }
            $http.post('/pos' + '/getPaginatedCustomerList?type=' +$scope.inactiveStatus+ '&searchText=' + $scope.customerSearchText, angular.toJson(paginationDetails)).then(function (response) {
                    var data = response.data;
                    console.log(data);
                $scope.customerList = data.list;
                $scope.first = data.firstPage;
                $scope.last = data.lastPage;
                $scope.prev = data.prevPage;
                $scope.next = data.nextPage;
                $scope.pageNo = data.pageNo;
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                }
            )
        };
        $scope.removeCustomerDetails = function () {
            $scope.customerNameText = "";
            $scope.GSTINText ="";
            $scope.customerEmailText = "";
            $scope.customerContactText = "";
            $scope.customerContactText = "";
            $scope.customerAddressText = "";
            $scope.personInchargeText = "";
            $scope.bankNameText = "";
            $scope.accNoText = "";
            $scope.panNumberText = "";
            $scope.bankBranchText = "";
            $scope.IFSCText = "";
            $scope.UINText = "";
            $scope.websiteText = "";
            $scope.creditLimitText = "";
            $scope.creditTermText = "";
            $scope.Hi_Conn_company_Name = false;
        };

        $scope.importPopup = function(){
            $("#import_Customer").modal('show');
        }

        $scope.editCustomerPopulate = function (dataObj) {
            $http.post('/pos'+ '/editCustomer?customerName=' + dataObj.customerName).then(function (response) {
                var data = response.data;
                $scope.customerId=data.customerId;
                $scope.customerNameText=data.customerName;
                $scope.customerContactText=data.customerContact;
                $scope.customerEmailText=data.customerEmail;
                $scope.customerAddressText=data.billingAddress;
                $scope.personInchargeText=data.personIncharge;
                $scope.stateDTOList =data.stateDTOList;
                $scope.countryDTOList = data.countryDTOList;
                $scope.currencyDTOList = data.currencyDTOList;
                $scope.GSTINText=data.gstIn;
                $scope.selectedState=data.state;
                $scope.selectedCountry=data.country;
                $scope.selectedCurrency=data.currency;
                $scope.bankNameText=data.bankName;
                $scope.accNoText=data.accountNo;
                $scope.panNumberText=data.panNO;
                $scope.bankBranchText=data.branchName;
                $scope.IFSCText=data.iFSCCode;
                $scope.UINText=data.uin;
                $scope.websiteText=data.website;
                $scope.creditTermText=data.creditTerm;
                $scope.creditLimitText=data.creditedLimit;
                $scope.custStatusText=data.status;
                $("#addCustomer").modal('show');
                $('#modal-title').text("Edit Customer");
                $("#submit").text("Update");
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                }
            )
        };
        $scope.getCustomerList();
        $scope.next_wizard = function(){
            $("#cus_step1").removeClass("in active");
            $("#cus_step2").addClass("tab-pane fade in active");
        }
        $scope.back_wizard = function(){
            $("#cus_step2").removeClass("in active");
            $("#cus_step1").addClass("tab-pane fade in active");
        }
        $scope.next_wizardEdit = function(){
            $("#cus_step12").removeClass("in active");
            $("#cus_step22").addClass("tab-pane fade in active");
        }
        $scope.back_wizardEdit = function(){
            $("#cus_step22").removeClass("in active");
            $("#cus_step12").addClass("tab-pane fade in active");
        }

    });
