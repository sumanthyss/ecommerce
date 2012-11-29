$ ->
  $.get "/items", (data) ->
    $.each data, (index, item) ->
      $('#items').append($('<li>')).append("<table><tr><td>#{item.name}</td></tr><tr><td><img src='#{item.imgPath}'</td></tr></table>")