$(function(){
   $('#keepImage').on('click', function(){
      $('#image').attr('disabled', $(this).is(':checked'));
   });
});