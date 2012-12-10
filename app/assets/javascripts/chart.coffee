Raphael.fn.pieChart = (cx, cy, r, values, labels, stroke) ->
  #cx is the x value of the circle's center
  #cy is the y value
  #r is the radius
  paper = @
  rad   = Math.PI / 180
  chart = @.set()
  sector = (cx, cy, r, startAngle, endAngle, params) ->
    x1 = cx + r * Math.cos(-startAngle * rad)
    x2 = cx + r * Math.cos(-endAngle * rad)
    y1 = cy + r * Math.sin(-startAngle * rad)
    y2 = cy + r * Math.sin(-endAngle * rad)
    paper.path(["M", cx, cy, "L", x1, y1, "A", r, r, 0, +(endAngle - startAngle > 180), 0, x2, y2, "z"]).attr(params)

  angle = 0
  total = 0
  start = 0
  indexX = 150
  indexY = 50
  process = (j) ->
    value = values[j]
    label = labels[j]
    percent = (value / total) * 100
    label += ": #{percent.toFixed(1)}%"
    angleplus = 360 * value / total
    popangle = angle + (angleplus / 2)
    color = Raphael.hsb(start, .75, 1)
    ms = 500
    delta = 30
    bcolor = Raphael.hsb(start, 1, 1)
    p = sector(cx, cy, r, angle, angle + angleplus, {fill: "90-#{bcolor}-#{color}", stroke: stroke, "stroke-width": 3})
    txt = paper.text(indexX, indexY, label).attr({fill: bcolor, stroke: "none", opacity: 0, "font-size": 20})
    p.mouseover( () ->
      p.stop().animate({transform: "s1.1 1.1 #{cx} #{cy}", ms, "elastic"})
      txt.stop().animate({opacity: 1}, ms, "elastic")
    ).mouseout () ->
      p.stop().animate({transform: ""}, ms, "elastic")
      txt.stop().animate({opacity: 0}, 100)
    angle += angleplus
    chart.push(p)
    chart.push(txt)
    start += .1
  for value in values
    total += value
  for i in [0...values.length]
    process(i)
  paper.text(cx, 25, "Percentage of Total Spent").attr({fill: "white", stroke: "none", "font-size": 30})
  return chart
