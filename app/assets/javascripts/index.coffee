$ ->
  $.get "/items", (data) ->
    $.each data, (index, item) ->
      $('#items').append $('<li>').text item.name