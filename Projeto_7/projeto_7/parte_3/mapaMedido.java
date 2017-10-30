import lejos.geom.*;
import lejos.robotics.mapping.LineMap;

public class mapaMedido{

  public static void main(String[] args){
    Line[] lines = {
new Line(391,396,281,292),
new Line(281,292,250,298),
new Line(391,396,310,317),
new Line(310,317,281,292),
new Line(281,292,330,295),
new Line(391,396,310,317),
new Line(310,317,281,292),
new Line(281,292,364,287),
new Line(364,287,344,293),
new Line(344,293,320,52),
new Line(320,52,348,48),
new Line(348,48,369,158),
new Line(369,158,424,116),
new Line(424,116,510,221),
new Line(391,396,310,317),
new Line(310,317,281,292),
new Line(281,292,258,298),
new Line(258,298,364,287),
new Line(364,287,344,293),
new Line(344,293,320,52),
new Line(320,52,341,48),
new Line(341,48,362,47),
new Line(362,47,369,158),
new Line(369,158,424,116),
new Line(424,116,445,129),
new Line(445,129,465,145),
new Line(465,145,481,163),
new Line(481,163,495,184),
new Line(495,184,506,206),
new Line(506,206,430,152),
new Line(430,152,450,154),
new Line(450,154,471,157),
new Line(471,157,495,161),
new Line(495,161,513,176),
new Line(513,176,463,276),
new Line(463,276,430,288),
new Line(391,396,310,317),
new Line(310,317,281,292),
new Line(281,292,258,298),
new Line(258,298,364,287),
new Line(364,287,344,293),
new Line(344,293,320,52),
new Line(320,52,355,47),
new Line(355,47,369,158),
new Line(369,158,424,116),
new Line(424,116,495,184),
new Line(495,184,507,211),
new Line(507,211,430,152),
new Line(430,152,453,154),
new Line(453,154,477,153),
new Line(477,153,498,163),
new Line(498,163,515,179),
new Line(515,179,463,276),
new Line(463,276,436,287),
new Line(436,287,482,238),
new Line(482,238,560,192),
new Line(560,192,641,220),
new Line(641,220,525,246),
new Line(525,246,631,241),
new Line(391,396,310,317),
new Line(310,317,281,292),
new Line(281,292,258,298),
new Line(258,298,364,287),
new Line(364,287,344,293),
new Line(344,293,320,52),
new Line(320,52,355,47),
new Line(355,47,369,158),
new Line(369,158,424,116),
new Line(424,116,492,179),
new Line(492,179,507,211),
new Line(507,211,430,152),
new Line(430,152,453,154),
new Line(453,154,477,153),
new Line(477,153,498,163),
new Line(498,163,515,179),
new Line(515,179,463,276),
new Line(463,276,436,287),
new Line(436,287,482,238),
new Line(482,238,560,192),
new Line(560,192,641,220),
new Line(641,220,525,246),
new Line(525,246,551,245),
new Line(551,245,631,241),
new Line(631,241,487,178),
new Line(487,178,506,162),
new Line(506,162,528,144),
new Line(528,144,608,49),
new Line(608,49,598,164),
new Line(598,164,652,50),
new Line(652,50,723,67),
new Line(723,67,710,218),
new Line(710,218,609,245),
new Line(609,245,659,250),
new Line(391,396,310,317),
new Line(310,317,281,292),
new Line(281,292,258,298),
new Line(258,298,364,287),
new Line(364,287,344,293),
new Line(344,293,320,52),
new Line(320,52,362,47),
new Line(362,47,369,158),
new Line(369,158,424,116),
new Line(424,116,475,155),
new Line(475,155,495,184),
new Line(495,184,506,206),
new Line(506,206,430,152),
new Line(430,152,453,154),
new Line(453,154,477,153),
new Line(477,153,498,163),
new Line(498,163,515,179),
new Line(515,179,463,276),
new Line(463,276,436,287),
new Line(436,287,482,238),
new Line(482,238,560,192),
new Line(560,192,641,220),
new Line(641,220,525,246),
new Line(525,246,551,245),
new Line(551,245,631,241),
new Line(631,241,487,178),
new Line(487,178,509,160),
new Line(509,160,528,144),
new Line(528,144,608,49),
new Line(608,49,598,164),
new Line(598,164,652,50),
new Line(652,50,723,67),
new Line(723,67,690,227),
new Line(690,227,609,245),
new Line(609,245,665,239),
new Line(665,239,609,247),
new Line(609,247,691,243),
new Line(691,243,608,221),
new Line(608,221,613,199),
new Line(613,199,495,91),
new Line(495,91,633,196),
new Line(633,196,585,110),
new Line(585,110,635,193),
new Line(635,193,621,85),
new Line(621,85,644,192),
new Line(644,192,626,52),
new Line(626,52,646,191),
new Line(646,191,699,173),
new Line(699,173,725,220),
new Line(391,396,310,317),
new Line(310,317,281,292),
new Line(281,292,258,298),
new Line(258,298,364,287),
new Line(364,287,344,293),
new Line(344,293,320,52),
new Line(320,52,341,48),
new Line(341,48,362,47),
new Line(362,47,369,158),
new Line(369,158,424,116),
new Line(424,116,465,145),
new Line(465,145,487,171),
new Line(487,171,499,193),
new Line(499,193,508,216),
new Line(508,216,430,152),
new Line(430,152,515,179),
new Line(515,179,463,276),
new Line(463,276,436,287),
new Line(436,287,482,238),
new Line(482,238,560,192),
new Line(560,192,641,220),
new Line(641,220,525,246),
new Line(525,246,631,241),
new Line(631,241,487,178),
new Line(487,178,512,157),
new Line(512,157,608,49),
new Line(608,49,598,164),
new Line(598,164,652,50),
new Line(652,50,723,67),
new Line(723,67,690,227),
new Line(690,227,609,245),
new Line(609,245,665,239),
new Line(665,239,609,247),
new Line(609,247,691,243),
new Line(691,243,650,248),
new Line(650,248,608,221),
new Line(608,221,613,192),
new Line(613,192,495,91),
new Line(495,91,633,196),
new Line(633,196,585,110),
new Line(585,110,635,193),
new Line(635,193,621,85),
new Line(621,85,644,192),
new Line(644,192,626,52),
new Line(626,52,646,191),
new Line(646,191,721,192),
new Line(721,192,724,215),
new Line(724,215,639,170),
new Line(639,170,577,64),
new Line(577,64,603,55),
new Line(603,55,628,47),
new Line(628,47,654,47),
new Line(654,47,680,49),
new Line(680,49,714,38),
new Line(714,38,736,51),
new Line(736,51,668,148),
new Line(668,148,701,117),
new Line(701,117,756,68),
new Line(756,68,676,146),
new Line(676,146,764,75),
new Line(764,75,687,141),
new Line(687,141,720,142),
new Line(720,142,723,170),
new Line(391,396,310,317),
new Line(310,317,281,292),
new Line(281,292,364,287),
new Line(364,287,344,293),
new Line(344,293,320,52),
new Line(320,52,341,48),
new Line(341,48,362,47),
new Line(362,47,369,158),
new Line(369,158,424,116),
new Line(424,116,457,139),
new Line(457,139,478,159),
new Line(478,159,492,179),
new Line(492,179,504,202),
new Line(504,202,430,152),
new Line(430,152,498,163),
new Line(498,163,518,182),
new Line(518,182,463,276),
new Line(463,276,436,287),
new Line(436,287,482,238),
new Line(482,238,560,192),
new Line(560,192,641,220),
new Line(641,220,525,246),
new Line(525,246,631,241),
new Line(631,241,487,178),
new Line(487,178,528,144),
new Line(528,144,608,49),
new Line(608,49,598,164),
new Line(598,164,652,50),
new Line(652,50,723,67),
new Line(723,67,710,218),
new Line(710,218,609,245),
new Line(609,245,665,239),
new Line(665,239,609,247),
new Line(609,247,691,243),
new Line(691,243,650,248),
new Line(650,248,608,221),
new Line(608,221,615,197),
new Line(615,197,495,91),
new Line(495,91,594,144),
new Line(594,144,633,196),
new Line(633,196,585,110),
new Line(585,110,635,193),
new Line(635,193,621,85),
new Line(621,85,644,192),
new Line(644,192,626,52),
new Line(626,52,646,191),
new Line(646,191,671,195),
new Line(671,195,703,179),
new Line(703,179,721,192),
new Line(721,192,724,215),
new Line(724,215,639,170),
new Line(639,170,577,64),
new Line(577,64,611,52),
new Line(611,52,645,47),
new Line(645,47,680,49),
new Line(680,49,714,38),
new Line(714,38,736,51),
new Line(736,51,668,148),
new Line(668,148,701,117),
new Line(701,117,756,68),
new Line(756,68,676,146),
new Line(676,146,764,75),
new Line(764,75,687,141),
new Line(687,141,720,142),
new Line(720,142,722,163),
new Line(722,163,476,111),
new Line(476,111,623,110),
new Line(623,110,539,87),
new Line(539,87,603,99),
new Line(603,99,489,46),
new Line(489,46,562,71),
new Line(562,71,607,90),
new Line(607,90,508,8),
new Line(508,8,614,83),
new Line(614,83,603,58),
new Line(603,58,623,78),
new Line(623,78,629,48),
new Line(629,48,661,46),
new Line(661,46,710,33),
new Line(710,33,719,110),
new Line(391,396,310,317),
new Line(310,317,281,292),
new Line(281,292,364,287),
new Line(364,287,344,293),
new Line(344,293,320,52),
new Line(320,52,341,48),
new Line(341,48,362,47),
new Line(362,47,369,158),
new Line(369,158,424,116),
new Line(424,116,457,139),
new Line(457,139,475,155),
new Line(475,155,490,175),
new Line(490,175,501,197),
new Line(501,197,510,221),
new Line(510,221,430,152),
new Line(430,152,464,157),
new Line(464,157,495,161),
new Line(495,161,513,176),
new Line(513,176,463,276),
new Line(463,276,502,230),
new Line(502,230,560,192),
new Line(560,192,641,220),
new Line(641,220,525,246),
new Line(525,246,551,245),
new Line(551,245,631,241),
new Line(631,241,487,178),
new Line(487,178,518,152),
new Line(518,152,608,49),
new Line(608,49,598,164),
new Line(598,164,652,50),
new Line(652,50,723,67),
new Line(723,67,710,218),
new Line(710,218,690,227),
new Line(690,227,609,245),
new Line(609,245,665,239),
new Line(665,239,609,247),
new Line(609,247,691,243),
new Line(691,243,650,248),
new Line(650,248,608,221),
new Line(608,221,615,197),
new Line(615,197,495,91),
new Line(495,91,594,144),
new Line(594,144,633,196),
new Line(633,196,585,110),
new Line(585,110,635,193),
new Line(635,193,626,52),
new Line(626,52,646,191),
new Line(646,191,671,195),
new Line(671,195,699,173),
new Line(699,173,721,195),
new Line(721,195,724,218),
new Line(724,218,639,170),
new Line(639,170,577,64),
new Line(577,64,628,47),
new Line(628,47,714,38),
new Line(714,38,732,48),
new Line(732,48,668,148),
new Line(668,148,701,117),
new Line(701,117,756,68),
new Line(756,68,676,146),
new Line(676,146,764,75),
new Line(764,75,687,141),
new Line(687,141,720,142),
new Line(720,142,723,170),
new Line(723,170,476,111),
new Line(476,111,623,110),
new Line(623,110,539,87),
new Line(539,87,603,99),
new Line(603,99,489,46),
new Line(489,46,562,71),
new Line(562,71,607,90),
new Line(607,90,569,60),
new Line(569,60,508,8),
new Line(508,8,614,83),
new Line(614,83,591,62),
new Line(591,62,623,78),
new Line(623,78,625,49),
new Line(625,49,710,33),
new Line(710,33,698,56),
new Line(698,56,718,40),
new Line(718,40,714,79),
new Line(714,79,719,103),
new Line(719,103,600,159),
new Line(600,159,564,235),
new Line(564,235,582,165),
new Line(582,165,543,227),
new Line(543,227,523,216),
new Line(523,216,506,201),
new Line(506,201,493,183),
new Line(493,183,482,163),
new Line(482,163,460,145),
new Line(460,145,457,120),
new Line(457,120,457,96),
new Line(457,96,509,95),
new Line(509,95,475,75),
new Line(475,75,555,94),
new Line(555,94,535,63),
new Line(535,63,502,29),
new Line(502,29,545,58),
new Line(545,58,600,43),
new Line(391,396,310,317),
new Line(310,317,281,292),
new Line(281,292,364,287),
new Line(364,287,336,294),
new Line(336,294,320,52),
new Line(320,52,362,47),
new Line(362,47,369,158),
new Line(369,158,424,116),
new Line(424,116,461,141),
new Line(461,141,478,159),
new Line(478,159,510,221),
new Line(510,221,430,152),
new Line(430,152,457,155),
new Line(457,155,481,154),
new Line(481,154,507,171),
new Line(507,171,463,276),
new Line(463,276,442,285),
new Line(442,285,482,238),
new Line(482,238,502,230),
new Line(502,230,560,192),
new Line(560,192,641,220),
new Line(641,220,525,246),
new Line(525,246,551,245),
new Line(551,245,631,241),
new Line(631,241,487,178),
new Line(487,178,506,162),
new Line(506,162,528,144),
new Line(528,144,608,49),
new Line(608,49,598,164),
new Line(598,164,652,50),
new Line(652,50,723,67),
new Line(723,67,710,218),
new Line(710,218,609,245),
new Line(609,245,665,239),
new Line(665,239,609,247),
new Line(609,247,691,243),
new Line(691,243,650,248),
new Line(650,248,608,221),
new Line(608,221,616,196),
new Line(616,196,495,91),
new Line(495,91,594,144),
new Line(594,144,633,196),
new Line(633,196,585,110),
new Line(585,110,635,193),
new Line(635,193,621,85),
new Line(621,85,644,192),
new Line(644,192,626,52),
new Line(626,52,646,191),
new Line(646,191,699,173),
new Line(699,173,723,202),
new Line(723,202,639,170),
new Line(639,170,577,64),
new Line(577,64,667,47),
new Line(667,47,732,48),
new Line(732,48,668,148),
new Line(668,148,701,117),
new Line(701,117,756,68),
new Line(756,68,676,146),
new Line(676,146,764,75),
new Line(764,75,687,141),
new Line(687,141,722,168),
new Line(722,168,476,111),
new Line(476,111,623,110),
new Line(623,110,539,87),
new Line(539,87,603,99),
new Line(603,99,489,46),
new Line(489,46,562,71),
new Line(562,71,607,90),
new Line(607,90,569,60),
new Line(569,60,508,8),
new Line(508,8,614,83),
new Line(614,83,591,62),
new Line(591,62,623,78),
new Line(623,78,607,56),
new Line(607,56,679,50),
new Line(679,50,710,33),
new Line(710,33,698,56),
new Line(698,56,733,59),
new Line(733,59,715,84),
new Line(715,84,718,106),
new Line(718,106,600,159),
new Line(600,159,582,192),
new Line(582,192,592,141),
new Line(592,141,564,235),
new Line(564,235,582,165),
new Line(582,165,555,232),
new Line(555,232,513,207),
new Line(513,207,460,145),
new Line(460,145,457,115),
new Line(457,115,457,90),
new Line(457,90,509,95),
new Line(509,95,460,81),
new Line(460,81,555,94),
new Line(555,94,535,63),
new Line(535,63,502,29),
new Line(502,29,545,58),
new Line(545,58,582,64),
new Line(582,64,590,44),
new Line(590,44,718,110),
new Line(718,110,717,131),
new Line(717,131,713,152),
new Line(713,152,695,197),
new Line(695,197,680,213),
new Line(680,213,628,149),
new Line(628,149,600,149),
new Line(600,149,568,237),
new Line(568,237,587,141),
new Line(587,141,547,229),
new Line(547,229,580,148),
new Line(580,148,535,223),
new Line(535,223,517,209),
new Line(517,209,554,149),
new Line(554,149,524,170),
new Line(524,170,569,133),
new Line(569,133,487,175),
new Line(487,175,470,111),
new Line(391,396,310,317),
new Line(310,317,281,292),
new Line(281,292,364,287),
new Line(364,287,336,294),
new Line(336,294,320,52),
new Line(320,52,341,48),
new Line(341,48,362,47),
new Line(362,47,369,158),
new Line(369,158,424,116),
new Line(424,116,445,129),
new Line(445,129,465,145),
new Line(465,145,481,163),
new Line(481,163,497,188),
new Line(497,188,510,221),
new Line(510,221,430,152),
new Line(430,152,457,155),
new Line(457,155,481,154),
new Line(481,154,501,165),
new Line(501,165,518,182),
new Line(518,182,463,276),
new Line(463,276,440,286),
new Line(440,286,482,238),
new Line(482,238,503,230),
new Line(503,230,560,192),
new Line(560,192,641,220),
new Line(641,220,525,246),
new Line(525,246,551,245),
new Line(551,245,631,241),
new Line(631,241,487,178),
new Line(487,178,512,157),
new Line(512,157,532,145),
new Line(532,145,608,49),
new Line(608,49,598,164),
new Line(598,164,652,50),
new Line(652,50,723,67),
new Line(723,67,710,218),
new Line(710,218,609,245),
new Line(609,245,665,239),
new Line(665,239,609,247),
new Line(609,247,691,243),
new Line(691,243,650,248),
new Line(650,248,495,91),
new Line(495,91,594,144),
new Line(594,144,633,196),
new Line(633,196,585,110),
new Line(585,110,635,193),
new Line(635,193,621,85),
new Line(621,85,644,192),
new Line(644,192,626,52),
new Line(626,52,646,191),
new Line(646,191,699,173),
new Line(699,173,724,213),
new Line(724,213,639,170),
new Line(639,170,577,64),
new Line(577,64,624,48),
new Line(624,48,645,47),
new Line(645,47,667,47),
new Line(667,47,688,51),
new Line(688,51,714,38),
new Line(714,38,732,48),
new Line(732,48,668,148),
new Line(668,148,701,117),
new Line(701,117,756,68),
new Line(756,68,676,146),
new Line(676,146,764,75),
new Line(764,75,687,141),
new Line(687,141,722,168),
new Line(722,168,476,111),
new Line(476,111,623,110),
new Line(623,110,539,87),
new Line(539,87,603,99),
new Line(603,99,489,46),
new Line(489,46,607,90),
new Line(607,90,569,60),
new Line(569,60,508,8),
new Line(508,8,614,83),
new Line(614,83,603,58),
new Line(603,58,623,78),
new Line(623,78,607,56),
new Line(607,56,710,33),
new Line(710,33,698,56),
new Line(698,56,718,40),
new Line(718,40,713,77),
new Line(713,77,718,98),
new Line(718,98,600,159),
new Line(600,159,582,192),
new Line(582,192,592,141),
new Line(592,141,564,235),
new Line(564,235,582,165),
new Line(582,165,555,232),
new Line(555,232,531,221),
new Line(531,221,506,201),
new Line(506,201,486,171),
new Line(486,171,460,145),
new Line(460,145,456,111),
new Line(456,111,509,95),
new Line(509,95,460,81),
new Line(460,81,555,94),
new Line(555,94,504,24),
new Line(504,24,545,58),
new Line(545,58,581,45),
new Line(581,45,718,110),
new Line(718,110,717,131),
new Line(717,131,713,152),
new Line(713,152,695,197),
new Line(695,197,680,213),
new Line(680,213,628,149),
new Line(628,149,600,149),
new Line(600,149,568,237),
new Line(568,237,587,141),
new Line(587,141,547,229),
new Line(547,229,580,148),
new Line(580,148,535,223),
new Line(535,223,509,204),
new Line(509,204,554,149),
new Line(554,149,524,170),
new Line(524,170,569,133),
new Line(569,133,487,175),
new Line(487,175,472,138),
new Line(472,138,470,111),
new Line(470,111,510,163),
new Line(510,163,489,162),
new Line(489,162,469,152),
new Line(469,152,458,125),
new Line(458,125,457,91),
new Line(457,91,510,56),
new Line(391,396,310,317),
new Line(310,317,281,292),
new Line(281,292,364,287),
new Line(364,287,336,294),
new Line(336,294,320,52),
new Line(320,52,355,47),
new Line(355,47,369,158),
new Line(369,158,424,116),
new Line(424,116,465,145),
new Line(465,145,481,163),
new Line(481,163,497,188),
new Line(497,188,510,221),
new Line(510,221,430,152),
new Line(430,152,457,155),
new Line(457,155,477,153),
new Line(477,153,498,163),
new Line(498,163,515,179),
new Line(515,179,463,276),
new Line(463,276,444,285),
new Line(444,285,482,238),
new Line(482,238,501,230),
new Line(501,230,560,192),
new Line(560,192,641,220),
new Line(641,220,525,246),
new Line(525,246,551,245),
new Line(551,245,631,241),
new Line(631,241,487,178),
new Line(487,178,506,162),
new Line(506,162,528,144),
new Line(528,144,608,49),
new Line(608,49,598,164),
new Line(598,164,652,50),
new Line(652,50,723,67),
new Line(723,67,710,218),
new Line(710,218,609,245),
new Line(609,245,665,239),
new Line(665,239,609,247),
new Line(609,247,691,243),
new Line(691,243,650,248),
new Line(650,248,612,201),
new Line(612,201,495,91),
new Line(495,91,594,144),
new Line(594,144,633,196),
new Line(633,196,585,110),
new Line(585,110,635,193),
new Line(635,193,621,85),
new Line(621,85,644,192),
new Line(644,192,626,52),
new Line(626,52,646,191),
new Line(646,191,699,173),
new Line(699,173,723,200),
new Line(723,200,639,170),
new Line(639,170,577,64),
new Line(577,64,658,47),
new Line(658,47,697,54),
new Line(697,54,723,43),
new Line(723,43,741,54),
new Line(741,54,668,148),
new Line(668,148,701,117),
new Line(701,117,756,68),
new Line(756,68,676,146),
new Line(676,146,764,75),
new Line(764,75,687,141),
new Line(687,141,722,165),
new Line(722,165,476,111),
new Line(476,111,623,110),
new Line(623,110,539,87),
new Line(539,87,603,99),
new Line(603,99,489,46),
new Line(489,46,562,71),
new Line(562,71,607,90),
new Line(607,90,569,60),
new Line(569,60,508,8),
new Line(508,8,614,83),
new Line(614,83,591,62),
new Line(591,62,623,78),
new Line(623,78,607,56),
new Line(607,56,675,48),
new Line(675,48,710,33),
new Line(710,33,704,62),
new Line(704,62,724,48),
new Line(724,48,713,82),
new Line(713,82,719,103),
new Line(719,103,600,159),
new Line(600,159,582,192),
new Line(582,192,592,141),
new Line(592,141,564,235),
new Line(564,235,582,165),
new Line(582,165,555,232),
new Line(555,232,513,207),
new Line(513,207,457,96),
new Line(457,96,509,95),
new Line(509,95,460,81),
new Line(460,81,555,94),
new Line(555,94,535,63),
new Line(535,63,502,29),
new Line(502,29,545,58),
new Line(545,58,582,64),
new Line(582,64,590,44),
new Line(590,44,718,110),
new Line(718,110,717,131),
new Line(717,131,713,152),
new Line(713,152,695,197),
new Line(695,197,680,213),
new Line(680,213,628,149),
new Line(628,149,600,149),
new Line(600,149,568,237),
new Line(568,237,587,141),
new Line(587,141,547,229),
new Line(547,229,580,148),
new Line(580,148,535,223),
new Line(535,223,517,209),
new Line(517,209,554,149),
new Line(554,149,524,170),
new Line(524,170,569,133),
new Line(569,133,487,175),
new Line(487,175,470,111),
new Line(470,111,510,163),
new Line(510,163,479,164),
new Line(479,164,456,103),
new Line(456,103,467,72),
new Line(467,72,488,84),
new Line(488,84,474,66),
new Line(474,66,496,77),
new Line(496,77,454,165),
new Line(454,165,454,125),
new Line(391,396,310,317),
new Line(310,317,281,292),
new Line(281,292,251,297),
new Line(251,297,364,287),
new Line(364,287,341,294),
new Line(341,294,320,52),
new Line(320,52,355,47),
new Line(355,47,369,158),
new Line(369,158,428,119),
new Line(428,119,507,211),
new Line(507,211,430,152),
new Line(430,152,487,158),
new Line(487,158,507,171),
new Line(507,171,463,276),
new Line(463,276,445,284),
new Line(445,284,482,238),
new Line(482,238,500,230),
new Line(500,230,560,192),
new Line(560,192,641,220),
new Line(641,220,525,246),
new Line(525,246,551,245),
new Line(551,245,631,241),
new Line(631,241,487,178),
new Line(487,178,502,164),
new Line(502,164,528,144),
new Line(528,144,608,49),
new Line(608,49,598,164),
new Line(598,164,652,50),
new Line(652,50,723,67),
new Line(723,67,710,218),
new Line(710,218,609,245),
new Line(609,245,665,239),
new Line(665,239,609,247),
new Line(609,247,691,243),
new Line(691,243,650,248),
new Line(650,248,612,201),
new Line(612,201,495,91),
new Line(495,91,594,144),
new Line(594,144,633,196),
new Line(633,196,585,110),
new Line(585,110,635,193),
new Line(635,193,621,85),
new Line(621,85,644,192),
new Line(644,192,626,52),
new Line(626,52,646,191),
new Line(646,191,699,173),
new Line(699,173,723,200),
new Line(723,200,639,170),
new Line(639,170,577,64),
new Line(577,64,701,54),
new Line(701,54,727,46),
new Line(727,46,668,148),
new Line(668,148,701,117),
new Line(701,117,756,68),
new Line(756,68,676,146),
new Line(676,146,764,75),
new Line(764,75,687,141),
new Line(687,141,722,168),
new Line(722,168,476,111),
new Line(476,111,623,110),
new Line(623,110,539,87),
new Line(539,87,603,99),
new Line(603,99,489,46),
new Line(489,46,562,71),
new Line(562,71,607,90),
new Line(607,90,569,60),
new Line(569,60,508,8),
new Line(508,8,614,83),
new Line(614,83,591,62),
new Line(591,62,623,78),
new Line(623,78,607,56),
new Line(607,56,710,33),
new Line(710,33,698,56),
new Line(698,56,718,40),
new Line(718,40,712,74),
new Line(712,74,718,96),
new Line(718,96,600,159),
new Line(600,159,582,192),
new Line(582,192,592,141),
new Line(592,141,564,235),
new Line(564,235,582,165),
new Line(582,165,555,232),
new Line(555,232,471,129),
new Line(471,129,457,101),
new Line(457,101,509,95),
new Line(509,95,460,76),
new Line(460,76,555,94),
new Line(555,94,535,63),
new Line(535,63,502,29),
new Line(502,29,545,58),
new Line(545,58,581,45),
new Line(581,45,718,110),
new Line(718,110,717,131),
new Line(717,131,713,152),
new Line(713,152,695,197),
new Line(695,197,680,213),
new Line(680,213,628,149),
new Line(628,149,600,149),
new Line(600,149,568,237),
new Line(568,237,587,141),
new Line(587,141,547,229),
new Line(547,229,580,148),
new Line(580,148,535,223),
new Line(535,223,517,209),
new Line(517,209,554,149),
new Line(554,149,524,170),
new Line(524,170,569,133),
new Line(569,133,487,175),
new Line(487,175,471,133),
new Line(471,133,510,163),
new Line(510,163,486,163),
new Line(486,163,456,112),
new Line(456,112,457,91),
new Line(457,91,480,63),
new Line(480,63,496,77),
new Line(496,77,454,165),
new Line(454,165,450,125),
new Line(450,125,343,78),
new Line(343,78,376,65),
new Line(376,65,406,67),
new Line(406,67,406,94),
new Line(406,94,477,60),
new Line(477,60,412,105),
new Line(391,396,310,317),
new Line(310,317,281,292),
new Line(281,292,253,297),
new Line(253,297,364,287),
new Line(364,287,341,294),
new Line(341,294,320,52),
new Line(320,52,341,48),
new Line(341,48,362,47),
new Line(362,47,369,158),
new Line(369,158,424,116),
new Line(424,116,449,132),
new Line(449,132,468,148),
new Line(468,148,484,167),
new Line(484,167,499,193),
new Line(499,193,430,152),
new Line(430,152,450,154),
new Line(450,154,477,153),
new Line(477,153,498,163),
new Line(498,163,515,179),
new Line(515,179,463,276),
new Line(463,276,444,285),
new Line(444,285,482,238),
new Line(482,238,500,230),
new Line(500,230,560,192),
new Line(560,192,641,220),
new Line(641,220,525,246),
new Line(525,246,551,245),
new Line(551,245,631,241),
new Line(631,241,487,178),
new Line(487,178,506,162),
new Line(506,162,528,144),
new Line(528,144,608,49),
new Line(608,49,598,164),
new Line(598,164,609,148),
new Line(609,148,652,50),
new Line(652,50,723,67),
new Line(723,67,710,218),
new Line(710,218,609,245),
new Line(609,245,665,239),
new Line(665,239,609,247),
new Line(609,247,691,243),
new Line(691,243,650,248),
new Line(650,248,608,221),
new Line(608,221,613,192),
new Line(613,192,495,91),
new Line(495,91,594,144),
new Line(594,144,633,196),
new Line(633,196,585,110),
new Line(585,110,635,193),
new Line(635,193,621,85),
new Line(621,85,644,192),
new Line(644,192,626,52),
new Line(626,52,646,191),
new Line(646,191,699,173),
new Line(699,173,723,205),
new Line(723,205,639,170),
new Line(639,170,577,64),
new Line(577,64,714,38),
new Line(714,38,732,48),
new Line(732,48,668,148),
new Line(668,148,701,117),
new Line(701,117,756,68),
new Line(756,68,676,146),
new Line(676,146,764,75),
new Line(764,75,687,141),
new Line(687,141,722,168),
new Line(722,168,476,111),
new Line(476,111,623,110),
new Line(623,110,489,46),
new Line(489,46,562,71),
new Line(562,71,607,90),
new Line(607,90,508,8),
new Line(508,8,614,83),
new Line(614,83,600,59),
new Line(600,59,623,78),
new Line(623,78,607,56),
new Line(607,56,710,33),
new Line(710,33,698,56),
new Line(698,56,718,40),
new Line(718,40,713,77),
new Line(713,77,718,98),
new Line(718,98,600,159),
new Line(600,159,582,192),
new Line(582,192,592,141),
new Line(592,141,564,235),
new Line(564,235,582,165),
new Line(582,165,555,232),
new Line(555,232,527,218),
new Line(527,218,500,194),
new Line(500,194,482,163),
new Line(482,163,460,145),
new Line(460,145,457,115),
new Line(457,115,457,90),
new Line(457,90,509,95),
new Line(509,95,460,76),
new Line(460,76,557,90),
new Line(557,90,535,63),
new Line(535,63,502,29),
new Line(502,29,545,58),
new Line(545,58,581,45),
new Line(581,45,718,110),
new Line(718,110,717,131),
new Line(717,131,713,152),
new Line(713,152,695,197),
new Line(695,197,680,213),
new Line(680,213,628,149),
new Line(628,149,590,156),
new Line(590,156,568,237),
new Line(568,237,587,141),
new Line(587,141,547,229),
new Line(547,229,580,148),
new Line(580,148,535,223),
new Line(535,223,517,209),
new Line(517,209,554,149),
new Line(554,149,524,170),
new Line(524,170,569,133),
new Line(569,133,487,175),
new Line(487,175,470,111),
new Line(470,111,498,163),
new Line(498,163,473,158),
new Line(473,158,458,83),
new Line(458,83,488,84),
new Line(488,84,474,66),
new Line(474,66,496,77),
new Line(496,77,454,165),
new Line(454,165,439,128),
new Line(439,128,343,78),
new Line(343,78,375,65),
new Line(375,65,406,94),
new Line(406,94,477,60),
new Line(477,60,413,97),
new Line(413,97,379,68),
new Line(379,68,414,61),
new Line(391,396,310,317),
new Line(310,317,281,292),
new Line(281,292,260,297),
new Line(260,297,364,287),
new Line(364,287,344,293),
new Line(344,293,320,52),
new Line(320,52,341,48),
new Line(341,48,362,47),
new Line(362,47,369,158),
new Line(369,158,424,116),
new Line(424,116,481,163),
new Line(481,163,501,197),
new Line(501,197,430,152),
new Line(430,152,477,153),
new Line(477,153,498,163),
new Line(498,163,515,179),
new Line(515,179,463,276),
new Line(463,276,444,285),
new Line(444,285,482,238),
new Line(482,238,503,230),
new Line(503,230,560,192),
new Line(560,192,641,220),
new Line(641,220,525,246),
new Line(525,246,631,241),
new Line(631,241,487,178),
new Line(487,178,515,155),
new Line(515,155,608,49),
new Line(608,49,598,164),
new Line(598,164,652,50),
new Line(652,50,723,67),
new Line(723,67,710,218),
new Line(710,218,609,245),
new Line(609,245,665,239),
new Line(665,239,609,247),
new Line(609,247,691,243),
new Line(691,243,650,248),
new Line(650,248,611,203),
new Line(611,203,495,91),
new Line(495,91,594,144),
new Line(594,144,633,196),
new Line(633,196,585,110),
new Line(585,110,635,193),
new Line(635,193,621,85),
new Line(621,85,644,192),
new Line(644,192,626,52),
new Line(626,52,646,191),
new Line(646,191,699,173),
new Line(699,173,723,202),
new Line(723,202,639,170),
new Line(639,170,577,64),
new Line(577,64,714,38),
new Line(714,38,732,48),
new Line(732,48,668,148),
new Line(668,148,701,117),
new Line(701,117,756,68),
new Line(756,68,676,146),
new Line(676,146,764,75),
new Line(764,75,687,141),
new Line(687,141,720,142),
new Line(720,142,723,170),
new Line(723,170,476,111),
new Line(476,111,623,110),
new Line(623,110,489,46),
new Line(489,46,562,71),
new Line(562,71,607,90),
new Line(607,90,508,8),
new Line(508,8,614,83),
new Line(614,83,600,59),
new Line(600,59,623,78),
new Line(623,78,607,56),
new Line(607,56,710,33),
new Line(710,33,698,56),
new Line(698,56,718,40),
new Line(718,40,713,77),
new Line(713,77,718,98),
new Line(718,98,600,159),
new Line(600,159,582,192),
new Line(582,192,592,141),
new Line(592,141,564,235),
new Line(564,235,582,165),
new Line(582,165,555,232),
new Line(555,232,480,159),
new Line(480,159,471,129),
new Line(471,129,457,101),
new Line(457,101,509,95),
new Line(509,95,460,76),
new Line(460,76,555,94),
new Line(555,94,535,63),
new Line(535,63,502,29),
new Line(502,29,545,58),
new Line(545,58,581,45),
new Line(581,45,718,110),
new Line(718,110,717,131),
new Line(717,131,713,152),
new Line(713,152,695,197),
new Line(695,197,680,213),
new Line(680,213,628,149),
new Line(628,149,600,149),
new Line(600,149,568,237),
new Line(568,237,587,141),
new Line(587,141,547,229),
new Line(547,229,580,148),
new Line(580,148,535,223),
new Line(535,223,517,209),
new Line(517,209,554,149),
new Line(554,149,524,170),
new Line(524,170,569,133),
new Line(569,133,487,175),
new Line(487,175,473,142),
new Line(473,142,470,115),
new Line(470,115,510,163),
new Line(510,163,479,164),
new Line(479,164,456,114),
new Line(456,114,457,94),
new Line(457,94,467,72),
new Line(467,72,488,84),
new Line(488,84,454,165),
new Line(454,165,436,130),
new Line(436,130,360,71),
new Line(360,71,396,66),
new Line(396,66,406,94),
new Line(406,94,477,60),
new Line(477,60,413,97),
new Line(413,97,382,65),
new Line(382,65,279,55),



      
    };
    //Rectangle(int x, int y, int width, int height)  -- always integer coordinates
    //Creates a rectangle with top left corner at (x,y) and with specified width and height.
    //Rectangle bounds = new Rectangle(0, -841, 1189, 841);  
    Rectangle bounds = new Rectangle(0, 0, 1000, 1000); 
    LineMap mymap = new LineMap(lines, bounds);

    try{
        mymap.createSVGFile("mapa.svg");
        mymap.flip().createSVGFile("mapaFlipY.svg"); //creates a fliped version in the Y-axis of the orginal image
    }catch (Exception e){
        System.out.print("Exception caught: ");
        System.out.println(e.getMessage());
    }
  }
}

