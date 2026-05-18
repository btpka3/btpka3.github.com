import { visit } from 'unist-util-visit';
import { deflateSync } from 'node:zlib';

function encodeKroki(source, language) {
  const compressed = deflateSync(Buffer.from(source, 'utf-8'));
  const encoded = compressed.toString('base64url');
  return `https://kroki.io/${language}/svg/${encoded}`;
}

export default function remarkPlantuml() {
  return (tree) => {
    visit(tree, 'code', (node, index, parent) => {
      if (!parent || index == null) return;
      if (node.lang !== 'plantuml') return;

      const url = encodeKroki(node.value, 'plantuml');

      parent.children[index] = {
        type: 'paragraph',
        children: [
          {
            type: 'image',
            url,
            alt: 'PlantUML Diagram',
          },
        ],
      };
    });
  };
}
