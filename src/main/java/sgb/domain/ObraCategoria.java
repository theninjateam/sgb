package sgb.domain;

import java.util.HashMap;
import java.util.List;

public class ObraCategoria {

        private AreaCientifica areaCientifica;

        private List<Obra> obras;


        public ObraCategoria(AreaCientifica areaCientifica, List<Obra> obras) {
            this.areaCientifica = areaCientifica;
            this.obras = obras;
        }

        public AreaCientifica getAreaCientifica() {
            return areaCientifica;
        }

        public List<Obra> getObras() {
            return obras;
        }

        public void setAreaCientifica(AreaCientifica areaCientifica) {
            this.areaCientifica = areaCientifica;
        }

        public void setObras(List<Obra> obras) {
            this.obras = obras;
        }
}
