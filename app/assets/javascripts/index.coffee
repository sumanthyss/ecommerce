$ ->
  $.get "/items", (data) ->
    $.each data, (index, item) ->
      $('#items').append($('<li>')).append("<table><tr><td>#{item.name}</td></tr><tr><td><img src='assets/images/#{item.name}.png'</td></tr></table>")