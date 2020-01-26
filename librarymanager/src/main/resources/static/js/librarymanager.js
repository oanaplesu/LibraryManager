$(function(){
   $('#keepImage').on('click', function(){
      $('#image').attr('disabled', $(this).is(':checked'));
   });
});

$("#show-form-copybook").click(function(){
$('#form-copybook').toggle("slow", function() {
    $('#form-copybook').get(0).scrollIntoView();
  });
});

$("#show-form-newloan").click(function(){
$('#form-newloan').toggle("slow", function() {
    $('#form-copybook').get(0).scrollIntoView();
  });
});