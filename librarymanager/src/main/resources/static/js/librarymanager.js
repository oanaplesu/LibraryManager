$(function(){
   $('#keepImage').on('click', function(){
      $('#image').attr('disabled', $(this).is(':checked'));
   });
});

$("#show-form-copybook").click(function(){
$('#form-copybook').toggle("fast", function() {
    $('#form-copybook').get(0).scrollIntoView();
  });
});