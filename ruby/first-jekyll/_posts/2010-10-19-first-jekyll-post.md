---
layout: post
title:  "first jekyll post!"
date:   2015-10-19 23:10:21
categories: jekyll update
tags:
  - tagA
  - tagB
---


# Contents
{:.no_toc}

* Will be replaced with the ToC, excluding the "Contents" header
{:toc}


# test include
{% include inc.md %}

## test code block

{{ site.time | date_to_xmlschema }}   {{ site.time | date: "%Y-%m-%d %H:%M:%S" }} {{ page.date | date: "%Y-%m-%d %H:%M:%S" }}

```ruby
def print_hi(name)
  puts "Hi, #{name}"
end
print_hi('Tom')
#=> prints 'Hi, Tom' to STDOUT.
```

## test loop

{% for user in site.data.user.users %}
* {{ user.name }}
{% endfor %}


# test auto code block
<script>console.log("XSS")</script>
<div style="color:red;z-index:111;">aaaaaaaaaaaaaaaaaaaa</div>

```
<?xml version="1.0" encoding="UTF-8"?>
<rss version="2.0" xmlns:atom="http://www.w3.org/2005/Atom">
  <channel>

  </channel>
</rss>
```

## test quote

> This is a blockquote with two paragraphs. Lorem ipsum dolor sit amet,
> consectetuer adipiscing elit. Aliquam hendrerit mi posuere lectus.
> Vestibulum enim wisi, viverra nec, fringilla in, laoreet vitae, risus.
>
> Donec sit amet nisl. Aliquam semper ipsum sit amet velit. Suspendisse
> id sem consectetuer libero luctus adipiscing.

## 中文标题1

### 中Eng混合标题1

## 中文标题2

### 中Eng混合标题2

<kbd>Ctrl+[</kbd> and <kbd>Ctrl+]</kbd>

---

{% for tag in page.tags %}
* == {{tag}}
{% endfor %}

### <i class="glyphicon glyphicon-music"></i> test MathJax

$$x = {-b \pm \sqrt{b^2-4ac} \over 2a}.$$

$$
\begin{align}
\mbox{Union: } & A\cup B = \{x\mid x\in A \mbox{ or } x\in B\} \\
\mbox{Concatenation: } & A\circ B  = \{xy\mid x\in A \mbox{ and } y\in B\} \\
\mbox{Star: } & A^\star  = \{x_1x_2\ldots x_k \mid  k\geq 0 \mbox{ and each } x_i\in A\} \\
\end{align}
$$
